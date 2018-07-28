package com.tatar.shoppinglist.ui.shoppinglistitem;

import android.util.Log;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.utils.StringUtils;

public class ShoppingListItemPresenterImpl implements ShoppingListItemContract.ShoppingListItemPresenter {

    private static final String TAG = ShoppingListItemPresenterImpl.class.getSimpleName();

    private String shoppingListId;

    private ShoppingListItemContract.ShoppingListItemView shoppingListItemView;
    private ItemDao itemDao;
    private ShoppingListDao shoppingListDao;

    public ShoppingListItemPresenterImpl(ShoppingListItemContract.ShoppingListItemView shoppingListItemView, ItemDao itemDao, ShoppingListDao shoppingListDao) {
        this.shoppingListItemView = shoppingListItemView;
        this.itemDao = itemDao;
        this.shoppingListDao = shoppingListDao;
    }

    @Override
    public void getActvItems() {
        try {
            ItemActvTask itemActvTask = new ItemActvTask(shoppingListItemView, itemDao);
            itemActvTask.execute();
        } catch (Exception e) {
            Log.e(TAG, "getActvItems: ", e);
            shoppingListItemView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void getShoppingListItems(String shoppingListId) {
        try {
            this.shoppingListId = shoppingListId;

            refreshAndDisplayShoppingListsItems(shoppingListId);
        } catch (Exception e) {
            Log.e(TAG, "getShoppingListItems: ", e);
            shoppingListItemView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void addItemToShoppingList(String name) {
        try {
            if (name != null && !name.isEmpty()) {
                String standardizedItemName = StringUtils.standardizeItemName(name);
                shoppingListDao.addItemToShoppingList(shoppingListId, standardizedItemName);
                shoppingListItemView.clearActv();
                shoppingListItemView.displayMessage("Item has been added to shopping list.");
                getActvItems();
                refreshAndDisplayShoppingListsItems(shoppingListId);
            } else {
                shoppingListItemView.displayMessage("Please enter an item name.");
            }
        } catch (Exception e) {
            Log.e(TAG, "addItemToShoppingList: ", e);
            shoppingListItemView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void removeItemFromShoppingList(int position) {
        try {
            shoppingListDao.removeItemFromShoppingList(shoppingListId, position);
            refreshAndDisplayShoppingListsItems(shoppingListId);
        } catch (Exception e) {
            Log.e(TAG, "removeItemFromShoppingList: ", e);
            shoppingListItemView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void updateIsCollectedForItem(String itemId, boolean isCollected) {
        shoppingListDao.updateIsCollectedForItem(itemId, isCollected);
    }

    private void refreshAndDisplayShoppingListsItems(String shoppingListId) {
        ShoppingListItemTask shoppingListItemTask = new ShoppingListItemTask(shoppingListItemView, shoppingListDao);
        shoppingListItemTask.execute(shoppingListId);
    }
}
