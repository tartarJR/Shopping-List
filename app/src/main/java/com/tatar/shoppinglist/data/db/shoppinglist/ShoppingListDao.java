package com.tatar.shoppinglist.data.db.shoppinglist;

import com.tatar.shoppinglist.data.db.item.model.Item;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;

import java.util.List;

public interface ShoppingListDao {

    String createShoppingList(String name);

    void updateShoppingList(String id, String name);

    void deleteShoppingList(String id);

    List<ShoppingList> getAllShoppingLists();

    void addItemToShoppingList(String id, Item item);

    void removeItemFromShoppingList(String id, Item item);
}
