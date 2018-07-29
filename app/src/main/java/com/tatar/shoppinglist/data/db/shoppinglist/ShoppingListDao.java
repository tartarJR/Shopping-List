package com.tatar.shoppinglist.data.db.shoppinglist;

import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem;

import java.util.List;

public interface ShoppingListDao {

    List<ShoppingList> getAllShoppingLists();

    List<ShoppingListItem> getShoppingListItemsById(String shoppingListId);

    String createShoppingList(String name);

    void updateShoppingListName(String shoppingListId, String name);

    void updateShoppingListCompleted(String shoppingListId, boolean isCompleted);

    void deleteShoppingList(String shoppingListId);

    void addItemToShoppingList(String shoppingListId, String name);

    void removeItemFromShoppingList(String shoppingListId, int position);

    void updateIsCollectedForItem(String itemId, boolean isCollected);
}
