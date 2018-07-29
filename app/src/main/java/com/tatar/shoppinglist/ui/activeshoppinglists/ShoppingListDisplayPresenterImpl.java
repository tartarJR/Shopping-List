package com.tatar.shoppinglist.ui.activeshoppinglists;

import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.utils.StringUtils;

import timber.log.Timber;

import static com.tatar.shoppinglist.ui.activeshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayPresenter;
import static com.tatar.shoppinglist.ui.activeshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayView;

public class ShoppingListDisplayPresenterImpl implements ShoppingListDisplayPresenter {

    private ShoppingListDisplayView shoppingListDisplayView;
    private ShoppingListDao shoppingListDao;

    public ShoppingListDisplayPresenterImpl(ShoppingListDisplayView shoppingListDisplayView, ShoppingListDao shoppingListDao) {
        this.shoppingListDisplayView = shoppingListDisplayView;
        this.shoppingListDao = shoppingListDao;
    }

    @Override
    public void loadShoppingLists() {
        try {
            refreshAndDisplayShoppingLists();
        } catch (Exception e) {
            Timber.e("loadShoppingLists: ", e);
            shoppingListDisplayView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void createShoppingList(String name) {
        try {
            String standardizedItemName = StringUtils.standardizeItemName(name);
            String id = shoppingListDao.createShoppingList(standardizedItemName);
            shoppingListDisplayView.displayMessage("Shopping list created.");
            refreshAndDisplayShoppingLists();
            shoppingListDisplayView.navigateToAddItemActivity(standardizedItemName, id);
        } catch (Exception e) {
            Timber.e("createShoppingList: ", e);
            shoppingListDisplayView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void updateShoppingList(String id, String name) {
        try {
            String standardizedItemName = StringUtils.standardizeItemName(name);
            shoppingListDao.updateShoppingList(id, standardizedItemName);
            refreshAndDisplayShoppingLists();
            shoppingListDisplayView.displayMessage("Shopping List updated.");
        } catch (Exception e) {
            Timber.e("updateShoppingList: ", e);
            shoppingListDisplayView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void deleteShoppingList(String id) {
        try {
            shoppingListDao.deleteShoppingList(id);
            refreshAndDisplayShoppingLists();
            shoppingListDisplayView.displayMessage("Shopping List deleted.");
        } catch (Exception e) {
            Timber.e("deleteShoppingList: ", e);
            shoppingListDisplayView.displayMessage("An error occurred, please try again later.");
        }
    }

    private void refreshAndDisplayShoppingLists() {
        ShoppingListTask shoppingListTask = new ShoppingListTask(shoppingListDisplayView, shoppingListDao);
        shoppingListTask.execute();
    }
}
