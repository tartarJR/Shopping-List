package com.tatar.shoppinglist.data.db.shoppinglist.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class ShoppingList extends RealmObject {

    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String IS_COMPLETED_FIELD = "isCompleted";

    @PrimaryKey
    private String id;
    private String name;
    @Index
    private boolean isCompleted;
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public RealmList<ShoppingListItem> getShoppingListItems() {
        return shoppingListItems;
    }

    public void setShoppingListItems(RealmList<ShoppingListItem> shoppingListItems) {
        this.shoppingListItems = shoppingListItems;
    }
}
