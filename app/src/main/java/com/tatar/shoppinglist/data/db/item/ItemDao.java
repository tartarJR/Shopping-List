package com.tatar.shoppinglist.data.db.item;

import com.tatar.shoppinglist.data.db.item.model.Item;

import java.util.List;

public interface ItemDao {
    void createItem(String name);

    void updateItem(String id, String name);

    void deleteItem(String id);

    List<Item> getAllItems();
}
