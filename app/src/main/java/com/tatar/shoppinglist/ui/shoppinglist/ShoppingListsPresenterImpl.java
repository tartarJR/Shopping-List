package com.tatar.shoppinglist.ui.shoppinglist;

import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;
import com.tatar.shoppinglist.ui.helpers.AlertDialogHelper;

import static com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsContract.ShoppingListsView;

public class ShoppingListsPresenterImpl implements ShoppingListsContract.ShoppingListsPresenter {

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

    }

    @Override
    public void createShoppingList(String name) {

    }

    @Override
    public void updateShoppingList(String id, String name) {

    }

    @Override
    public void deleteShoppingList(String id) {

    }

    @Override
    public void displayAddOrUpdateShoppingListDialog() {

    }

    @Override
    public void displayActionsDialog(ShoppingList shoppingList) {

    }
}
