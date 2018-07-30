package com.tatar.shoppinglist.ui.completedshoppinglists;

import com.tatar.shoppinglist.data.network.model.RemoteShoppingList;
import com.tatar.shoppinglist.ui.BaseView;

import java.util.List;

public interface ShoppingListDisplayContract {
    interface ShoppingListDisplayView extends BaseView {
        void displayCompletedShoppingLists(List<RemoteShoppingList> remoteShoppingLists);

        void navigateToItemDisplayActivity(RemoteShoppingList remoteShoppingList);
    }

    interface ShoppingListDisplayPresenter {
        void loadCompletedShoppingLists(String userId);

        String getUserId();
    }
}
