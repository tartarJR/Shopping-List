package com.tatar.shoppinglist.data.db.shoppinglist;

import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem;

import java.util.List;

public interface ShoppingListDao {

    String createShoppingList(String name);

    void updateShoppingList(String shoppingListId, String name);

    void deleteShoppingList(String shoppingListId);

    List<ShoppingList> getAllShoppingLists();

    List<ShoppingListItem> getShoppingListItemsById(String shoppingListId);

    void addItemToShoppingList(String shoppingListId, String name);

    void removeItemFromShoppingList(String shoppingListId, int position);

    void switchIsCollected(String itemId);
}
