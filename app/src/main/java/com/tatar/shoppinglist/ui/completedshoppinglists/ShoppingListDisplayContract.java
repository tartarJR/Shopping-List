package com.tatar.shoppinglist.ui.completedshoppinglists;

import com.tatar.shoppinglist.data.network.model.ShoppingList;
import com.tatar.shoppinglist.ui.BaseView;

import java.util.List;

public interface ShoppingListDisplayContract {
    interface ShoppingListDisplayView extends BaseView {
        void displayCompletedShoppingLists(List<ShoppingList> shoppingLists);

        void navigateToItemDisplayActivity(ShoppingList shoppingList);
    }

    interface ShoppingListDisplayPresenter {
        void loadCompletedShoppingLists(String userId);
    }
}
