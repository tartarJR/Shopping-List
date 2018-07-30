package com.tatar.shoppinglist.ui.manageitems;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.utils.StringUtils;

import timber.log.Timber;

import static com.tatar.shoppinglist.ui.manageitems.ItemsContract.ItemsPresenter;
import static com.tatar.shoppinglist.ui.manageitems.ItemsContract.ItemsView;

/**
 * An Android free presenter class for ItemsActivity to manage its business logic
 */
public class ItemsPresenterImpl implements ItemsPresenter {

    private ItemsView itemsView;
    private ItemDao itemDao;

    public ItemsPresenterImpl(ItemsView itemsView, ItemDao itemDao) {
        this.itemsView = itemsView;
        this.itemDao = itemDao;
    }

    @Override
    public void loadItems() {
        try {
            refreshAndDisplayItemsList();
        } catch (Exception e) {
            Timber.e("loadItems: ", e);
            itemsView.showErrorMessage();
        }
    }

    @Override
    public void createItem(String name) {
        try {
            String standardizedItemName = StringUtils.standardizeItemName(name);

            if (itemDao.createItem(standardizedItemName)) {
                refreshAndDisplayItemsList();
                itemsView.showItemCreatedMessage();
            } else {
                itemsView.showValidationMessage();
            }
        } catch (Exception e) {
            Timber.e("createItem: ", e);
            itemsView.showErrorMessage();
        }
    }

    @Override
    public void updateItem(String id, String name) {
        try {
            String standardizedItemName = StringUtils.standardizeItemName(name);

            if (itemDao.updateItem(id, standardizedItemName)) {
                refreshAndDisplayItemsList();
                itemsView.showItemUpdatedMessage();
            } else {
                itemsView.showValidationMessage();
            }
        } catch (Exception e) {
            Timber.e("updateItem: ", e);
            itemsView.showErrorMessage();
        }
    }

    @Override
    public void deleteItem(String id) {
        try {
            itemDao.deleteItem(id);
            refreshAndDisplayItemsList();
            itemsView.showItemDeletedMessage();
        } catch (Exception e) {
            Timber.e("deleteItem: ", e);
            itemsView.showErrorMessage();
        }
    }

    private void refreshAndDisplayItemsList() {
        ItemsTask itemsTask = new ItemsTask(itemsView, itemDao);
        itemsTask.execute();
    }
}
