package com.tatar.shoppinglist.ui.completedshoppinglist;

import com.tatar.shoppinglist.data.network.model.ShoppingList;
import com.tatar.shoppinglist.ui.BaseView;

import java.util.List;

public interface CompletedShoppingListsContract {
    interface CompletedShoppingListsView extends BaseView {
        void displayCompletedShoppingLists(List<ShoppingList> shoppingLists);

        void navigateToItemDisplayActivity(ShoppingList shoppingList);
    }

    interface CompletedShoppingListsPresenter {
        void loadCompletedShoppingLists(String userId);
    }
}
