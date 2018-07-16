package com.tatar.shoppinglist.data.db.shoppinglist;

import com.tatar.shoppinglist.data.db.item.model.Item;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;

import java.util.List;

public interface ShoppingListDao {

    void createShoppingList();

    void updateShoppingList();

    void deleteShoppingList();

    ShoppingList getShoppingList(String id);

    List<ShoppingList> getAllShoppingLists();

    void addItemToShoppingList(Item item);

    void removeItemFromShoppingList(String id);
}
