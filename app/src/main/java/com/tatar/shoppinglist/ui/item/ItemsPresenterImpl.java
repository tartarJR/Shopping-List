package com.tatar.shoppinglist.ui.item;

import android.util.Log;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.data.db.item.model.Item;
import com.tatar.shoppinglist.utils.StringUtils;
import com.tatar.shoppinglist.utils.ui.AlertDialogHelper;

import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsPresenter;
import static com.tatar.shoppinglist.ui.item.ItemsContract.ItemsView;

/**
 * An Android free presenter class for ItemsActivity to manage its business logic
 */
public class ItemsPresenterImpl implements ItemsPresenter {

    private static final String TAG = ItemsPresenterImpl.class.getSimpleName();

    private ItemsView itemsView;
    private ItemDao itemDao;
    private AlertDialogHelper alertDialogHelper;

    public ItemsPresenterImpl(ItemsView itemsView, ItemDao itemDao, AlertDialogHelper alertDialogHelper) {
        this.itemsView = itemsView;
        this.itemDao = itemDao;
        this.alertDialogHelper = alertDialogHelper;
    }

    @Override
    public void loadItems() {
        try {
            refreshAndDisplayItemsList();
        } catch (Exception e) {
            Log.e(TAG, "loadItems: ", e);
            itemsView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void createItem(String name) {
        try {
            String standardizedItemName = StringUtils.standardizeItemName(name);

            if (itemDao.createItem(standardizedItemName)) {
                refreshAndDisplayItemsList();
                itemsView.displayMessage("Item created.");
            } else {
                itemsView.displayMessage("This item is already created.");
            }
        } catch (Exception e) {
            Log.e(TAG, "createItem: ", e);
            itemsView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void updateItem(String id, String name) {
        try {
            String standardizedItemName = StringUtils.standardizeItemName(name);

            if (itemDao.updateItem(id, standardizedItemName)) {
                refreshAndDisplayItemsList();
                itemsView.displayMessage("Item updated.");
            } else {
                itemsView.displayMessage("There is an item already created with that name.");
            }
        } catch (Exception e) {
            Log.e(TAG, "createItem: ", e);
            itemsView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void deleteItem(String id) {
        try {
            itemDao.deleteItem(id);
            refreshAndDisplayItemsList();
            itemsView.displayMessage("Item deleted.");
        } catch (Exception e) {
            Log.e(TAG, "deleteItem: ", e);
            itemsView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void displayAddItemDialog() {
        alertDialogHelper.displayAddItemDialog();
    }

    @Override
    public void displayActionsDialog(Item item) {
        alertDialogHelper.setUpAndDisplayActionsDialog(item);
    }

    private void refreshAndDisplayItemsList() {
        ItemsTask itemsTask = new ItemsTask(itemsView, itemDao);
        itemsTask.execute();
    }
}
