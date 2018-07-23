package com.tatar.shoppinglist.ui.shoppinglist;

import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;

import java.util.List;

public interface ShoppingListsContract {

    interface ShoppingListsView {
        void displayShoppingLists(List<ShoppingList> shoppingLists);

        void toggleNoShoppingListTv(int size);

        void navigateToAddItemActivity();

        void toggleProgressBar();

        void displayMessage(String message);
    }

    interface ShoppingListsPresenter {

        void loadShoppingLists();

        void createShoppingList(String name);

        void updateShoppingList(String id, String name);

        void deleteShoppingList(String id);

        void displayCreateShoppingListDialog();

        void displayActionsDialog(ShoppingList shoppingList);
    }

}
