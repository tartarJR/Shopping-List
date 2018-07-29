package com.tatar.shoppinglist.ui.activeshoppinglistitems;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.data.network.ShoppingListService;
import com.tatar.shoppinglist.data.prefs.SharedPreferencesManager;
import com.tatar.shoppinglist.utils.StringUtils;

import timber.log.Timber;

import static com.tatar.shoppinglist.data.network.ShoppingListService.PostShoppingListCallback;
import static com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayContract.ItemDisplayPresenter;
import static com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayContract.ItemDisplayView;

public class ItemDisplayPresenterImpl implements ItemDisplayPresenter, PostShoppingListCallback {

    private String shoppingListId;
    private String shoppingListName;

    private ItemDisplayView itemDisplayView;
    private ItemDao itemDao;
    private ShoppingListDao shoppingListDao;
    private ShoppingListService shoppingListService;
    private SharedPreferencesManager sharedPreferencesManager;

    public ItemDisplayPresenterImpl(ItemDisplayView itemDisplayView, ItemDao itemDao, ShoppingListDao shoppingListDao, ShoppingListService shoppingListService, SharedPreferencesManager sharedPreferencesManager) {
        this.itemDisplayView = itemDisplayView;
        this.itemDao = itemDao;
        this.shoppingListDao = shoppingListDao;
        this.shoppingListService = shoppingListService;
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    @Override
    public void getActvItems() {
        try {
            ActvTask actvTask = new ActvTask(itemDisplayView, itemDao);
            actvTask.execute();
        } catch (Exception e) {
            Timber.e("getActvItems: ", e);
            itemDisplayView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void getShoppingListItems(String shoppingListId, String shoppingListName) {
        try {
            this.shoppingListId = shoppingListId;
            this.shoppingListName = shoppingListName;

            refreshAndDisplayShoppingListsItems(shoppingListId);
        } catch (Exception e) {
            Timber.e("getShoppingListItems: ", e);
            itemDisplayView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void addItemToShoppingList(String name) {
        try {
            if (name != null && !name.isEmpty()) {
                String standardizedItemName = StringUtils.standardizeItemName(name);
                shoppingListDao.addItemToShoppingList(shoppingListId, standardizedItemName);
                itemDisplayView.clearActv();
                itemDisplayView.displayMessage("Item has been added to shopping list.");
                getActvItems();
                refreshAndDisplayShoppingListsItems(shoppingListId);
            } else {
                itemDisplayView.displayMessage("Please enter an item name.");
            }
        } catch (Exception e) {
            Timber.e("addItemToShoppingList: ", e);
            itemDisplayView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void removeItemFromShoppingList(int position) {
        try {
            shoppingListDao.removeItemFromShoppingList(shoppingListId, position);
            refreshAndDisplayShoppingListsItems(shoppingListId);
        } catch (Exception e) {
            Timber.e("removeItemFromShoppingList: ", e);
            itemDisplayView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void updateIsCollectedForItem(String itemId, boolean isCollected) {
        try {
            shoppingListDao.updateIsCollectedForItem(itemId, isCollected);
        } catch (Exception e) {
            Timber.e("updateIsCollectedForItem: ", e);
            itemDisplayView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void completeShopping() {
        try {
            RemoteItemsTask remoteItemsTask = new RemoteItemsTask(itemDisplayView, shoppingListDao, shoppingListService, sharedPreferencesManager, ItemDisplayPresenterImpl.this);
            remoteItemsTask.execute(shoppingListId, shoppingListName);
        } catch (Exception e) {
            Timber.e("updateIsCollectedForItem: ", e);
            itemDisplayView.displayMessage("An error occurred, please try again later.");
        }
    }

    private void refreshAndDisplayShoppingListsItems(String shoppingListId) {
        ItemsTask itemsTask = new ItemsTask(itemDisplayView, shoppingListDao);
        itemsTask.execute(shoppingListId);
    }

    @Override
    public void onFailure() {
        itemDisplayView.displayMessage("An error occurred, please try again later.");
    }

    @Override
    public void onPostSuccess() {
        itemDisplayView.displayMessage("Shopping is done !");
    }
}
