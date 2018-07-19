package com.tatar.shoppinglist.data.db.item;

import com.tatar.shoppinglist.data.db.item.model.Item;

import java.util.List;

public interface ItemDao {
    boolean createItem(String name);

    boolean updateItem(String id, String name);

    void deleteItem(String id);

    List<Item> getAllItems();
}
