package com.tatar.shoppinglist.data.db.shoppinglist.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ShoppingListItem extends RealmObject {

    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String IS_COLLECTED_FIELD = "isCollected";

    @PrimaryKey
    private String id;
    private String name;
    private boolean isCollected;

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

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }
}
