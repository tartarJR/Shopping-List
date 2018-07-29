package com.tatar.shoppinglist.ui.activeshoppinglistitems;

import com.tatar.shoppinglist.data.db.item.model.Item;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem;
import com.tatar.shoppinglist.ui.BaseView;

import java.util.List;

public interface ItemDisplayContract {
    interface ItemDisplayView extends BaseView {
        void populateItemActv(List<Item> items);

        void displayShoppingListItems(List<ShoppingListItem> shoppingListItems);

        void clearActv();
    }

    interface ItemDisplayPresenter {
        void getActvItems();

        void getShoppingListItems(String shoppingListId);

        void addItemToShoppingList(String name);

        void removeItemFromShoppingList(int position);

        void updateIsCollectedForItem(String itemId, boolean isCollected);
    }
}
