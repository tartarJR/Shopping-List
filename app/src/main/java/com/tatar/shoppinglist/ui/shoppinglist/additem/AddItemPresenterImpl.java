package com.tatar.shoppinglist.ui.shoppinglist.additem;

import android.util.Log;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.utils.StringUtils;

import static com.tatar.shoppinglist.ui.shoppinglist.additem.AddItemContract.AddItemPresenter;
import static com.tatar.shoppinglist.ui.shoppinglist.additem.AddItemContract.AddItemView;

public class AddItemPresenterImpl implements AddItemPresenter {

    private static final String TAG = AddItemPresenterImpl.class.getSimpleName();

    private AddItemView addItemView;
    private ItemDao itemDao;
    private ShoppingListDao shoppingListDao;

    public AddItemPresenterImpl(AddItemView addItemView, ItemDao itemDao, ShoppingListDao shoppingListDao) {
        this.addItemView = addItemView;
        this.itemDao = itemDao;
        this.shoppingListDao = shoppingListDao;
    }

    @Override
    public void getActvItems() {
        try {
            ItemActvTask itemActvTask = new ItemActvTask(addItemView, itemDao);
            itemActvTask.execute();
        } catch (Exception e) {
            Log.e(TAG, "getActvItems: ", e);
            addItemView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void getShoppingListItems(String shoppingListId) {
        try {
            refreshAndDisplayShoppingListsItems(shoppingListId);
        } catch (Exception e) {
            Log.e(TAG, "getShoppingListItems: ", e);
            addItemView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void addItemToShoppingList(String shoppingListId, String name) {
        try {
            if (name != null && !name.isEmpty()) {
                String standardizedItemName = StringUtils.standardizeItemName(name);
                shoppingListDao.addItemToShoppingList(shoppingListId, standardizedItemName);
                addItemView.clearActv();
                addItemView.displayMessage("Item has been added to shopping list.");
                getActvItems();
                refreshAndDisplayShoppingListsItems(shoppingListId);
            } else {
                addItemView.displayMessage("Please enter an item name.");
            }
        } catch (Exception e) {
            Log.e(TAG, "addItemToShoppingList: ", e);
            addItemView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void deleteItemFromShoppingList() {

    }

    @Override
    public void collectOrUnCollectItem() {

    }

    private void refreshAndDisplayShoppingListsItems(String shoppingListId) {
        ShoppingListItemsTask shoppingListItemsTask = new ShoppingListItemsTask(addItemView, shoppingListDao);
        shoppingListItemsTask.execute(shoppingListId);
    }
}
