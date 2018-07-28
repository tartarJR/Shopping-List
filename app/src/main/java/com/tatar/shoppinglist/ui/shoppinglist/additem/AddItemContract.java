package com.tatar.shoppinglist.ui.shoppinglist.additem;

import com.tatar.shoppinglist.data.db.item.model.Item;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem;

import java.util.List;

public interface AddItemContract {
    interface AddItemView {
        void populateItemActv(List<Item> items);

        void displayShoppingListItems(List<ShoppingListItem> shoppingListItems);

        void toggleProgressBar();

        void toggleNoItemsTv(int size);

        void displayMessage(String message);

        void clearActv();
    }

    interface AddItemPresenter {
        void getActvItems();

        void getShoppingListItems(String shoppingListId);

        void addItemToShoppingList(String name);

        void removeItemFromShoppingList(int position);

        void collectOrUnCollectItem();
    }
}
