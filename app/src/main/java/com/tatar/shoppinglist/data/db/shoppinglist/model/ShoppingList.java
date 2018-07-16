package com.tatar.shoppinglist.data.db.shoppinglist.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ShoppingList extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private String note;
    private RealmList<ShoppingListItem> shoppingListItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public RealmList<ShoppingListItem> getShoppingListItems() {
        return shoppingListItems;
    }

    public void setShoppingListItems(RealmList<ShoppingListItem> shoppingListItems) {
        this.shoppingListItems = shoppingListItems;
    }
}
