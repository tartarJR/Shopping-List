package com.tatar.shoppinglist.ui.activeshoppinglists;

import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;
import com.tatar.shoppinglist.ui.BaseView;

import java.util.List;

public interface ShoppingListDisplayContract {

    interface ShoppingListDisplayView extends BaseView {
        void displayShoppingLists(List<ShoppingList> shoppingLists);

        void navigateToAddItemActivity(String title, String id);
    }

    interface ShoppingListDisplayPresenter {

        void loadShoppingLists();

        void createShoppingList(String name);

        void updateShoppingList(String id, String name);

        void deleteShoppingList(String id);
    }

}
