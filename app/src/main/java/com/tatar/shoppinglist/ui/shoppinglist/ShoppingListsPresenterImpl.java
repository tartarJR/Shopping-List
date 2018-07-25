package com.tatar.shoppinglist.ui.shoppinglist;

import android.util.Log;

import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;
import com.tatar.shoppinglist.utils.StringUtils;
import com.tatar.shoppinglist.utils.ui.AlertDialogHelper;

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
            String id = shoppingListDao.createShoppingList(standardizedItemName);
            shoppingListsView.displayMessage("Shopping list created.");
            refreshAndDisplayShoppingLists();
            shoppingListsView.navigateToAddItemActivity(standardizedItemName, id);
        } catch (Exception e) {
            Log.e(TAG, "createShoppingList: ", e);
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
            Log.e(TAG, "updateShoppingList: ", e);
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
            Log.e(TAG, "deleteItem: ", e);
            shoppingListsView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void displayCreateShoppingListDialog() {
        alertDialogHelper.displayCreateShoppingListAlertDialog();
    }

    @Override
    public void displayActionsDialog(ShoppingList shoppingList) {
        alertDialogHelper.setUpAndDisplayActionsDialog(shoppingList.getId(), shoppingList.getName());
    }

    private void refreshAndDisplayShoppingLists() {
        ShoppingListTask shoppingListTask = new ShoppingListTask(shoppingListsView, shoppingListDao);
        shoppingListTask.execute();
    }
}
