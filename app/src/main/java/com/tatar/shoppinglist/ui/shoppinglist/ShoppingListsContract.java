package com.tatar.shoppinglist.ui.shoppinglist;

import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;
import com.tatar.shoppinglist.ui.BaseView;

import java.util.List;

public interface ShoppingListsContract {

    interface ShoppingListsView extends BaseView {
        void displayShoppingLists(List<ShoppingList> shoppingLists);

        void navigateToAddItemActivity(String title, String id);
    }

    interface ShoppingListsPresenter {

        void loadShoppingLists();

        void createShoppingList(String name);

        void updateShoppingList(String id, String name);

        void deleteShoppingList(String id);
    }

}
