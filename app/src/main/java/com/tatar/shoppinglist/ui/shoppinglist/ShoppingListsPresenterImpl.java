package com.tatar.shoppinglist.ui.shoppinglist;

import android.util.Log;

import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;
import com.tatar.shoppinglist.utils.ui.AlertDialogHelper;
import com.tatar.shoppinglist.utils.StringUtils;

import static com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsContract.ShoppingListsView;

public class ShoppingListsPresenterImpl implements ShoppingListsContract.ShoppingListsPresenter {

    private static final String TAG = ShoppingListsPresenterImpl.class.getSimpleName();

    private ShoppingListsView shoppingListsView;
    private ShoppingListDao shoppingListDao;
    private AlertDialogHelper alertDialogHelper;

    public ShoppingListsPresenterImpl(ShoppingListsView shoppingListsView, ShoppingListDao shoppingListDao, AlertDialogHelper alertDialogHelper) {
        this.shoppingListsView = shoppingListsView;
        this.shoppingListDao = shoppingListDao;
        this.alertDialogHelper = alertDialogHelper;
    }

    @Override
    public void loadShoppingLists() {
        try {
            refreshAndDisplayShoppingLists();
        } catch (Exception e) {
            Log.e(TAG, "loadShoppingLists: ", e);
            shoppingListsView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void createShoppingList(String name) {
        try {
            String standardizedItemName = StringUtils.standardizeItemName(name);
            shoppingListDao.createShoppingList(standardizedItemName);
            shoppingListsView.displayMessage("Shopping list created.");
            refreshAndDisplayShoppingLists();
            shoppingListsView.navigateToAddItemActivity(standardizedItemName);
        } catch (Exception e) {
            Log.e(TAG, "createShoppingList: ", e);
            shoppingListsView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void updateShoppingList(String id, String name) {

    }

    @Override
    public void deleteShoppingList(String id) {
    }

    @Override
    public void displayCreateShoppingListDialog() {
        alertDialogHelper.setUpAndDisplayShoppingListAlertDialog();
    }

    @Override
    public void displayActionsDialog(ShoppingList shoppingList) {

    }

    private void refreshAndDisplayShoppingLists() {
        ShoppingListTask shoppingListTask = new ShoppingListTask(shoppingListsView, shoppingListDao);
        shoppingListTask.execute();
    }
}
