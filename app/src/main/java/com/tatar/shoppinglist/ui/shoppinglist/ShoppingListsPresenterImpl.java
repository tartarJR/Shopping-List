package com.tatar.shoppinglist.ui.shoppinglist;

import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.utils.StringUtils;

import timber.log.Timber;

import static com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsContract.ShoppingListsPresenter;
import static com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsContract.ShoppingListsView;

public class ShoppingListsPresenterImpl implements ShoppingListsPresenter {

    private ShoppingListsView shoppingListsView;
    private ShoppingListDao shoppingListDao;

    public ShoppingListsPresenterImpl(ShoppingListsView shoppingListsView, ShoppingListDao shoppingListDao) {
        this.shoppingListsView = shoppingListsView;
        this.shoppingListDao = shoppingListDao;
    }

    @Override
    public void loadShoppingLists() {
        try {
            refreshAndDisplayShoppingLists();
        } catch (Exception e) {
            Timber.e("loadShoppingLists: ", e);
            shoppingListsView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void createShoppingList(String name) {
        try {
            String standardizedItemName = StringUtils.standardizeItemName(name);
            String id = shoppingListDao.createShoppingList(standardizedItemName);
            shoppingListsView.displayMessage("Shopping list created.");
            refreshAndDisplayShoppingLists();
            shoppingListsView.navigateToAddItemActivity(standardizedItemName, id);
        } catch (Exception e) {
            Timber.e("createShoppingList: ", e);
            shoppingListsView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void updateShoppingList(String id, String name) {
        try {
            String standardizedItemName = StringUtils.standardizeItemName(name);
            shoppingListDao.updateShoppingList(id, standardizedItemName);
            refreshAndDisplayShoppingLists();
            shoppingListsView.displayMessage("Shopping List updated.");
        } catch (Exception e) {
            Timber.e("updateShoppingList: ", e);
            shoppingListsView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void deleteShoppingList(String id) {
        try {
            shoppingListDao.deleteShoppingList(id);
            refreshAndDisplayShoppingLists();
            shoppingListsView.displayMessage("Shopping List deleted.");
        } catch (Exception e) {
            Timber.e("deleteShoppingList: ", e);
            shoppingListsView.displayMessage("An error occurred, please try again later.");
        }
    }

    private void refreshAndDisplayShoppingLists() {
        ShoppingListTask shoppingListTask = new ShoppingListTask(shoppingListsView, shoppingListDao);
        shoppingListTask.execute();
    }
}
