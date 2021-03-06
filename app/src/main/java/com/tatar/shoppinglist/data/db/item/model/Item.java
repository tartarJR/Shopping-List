package com.tatar.shoppinglist.data.db.item.model;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class Item extends RealmObject {

    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";

    @PrimaryKey
    private String id;
    @Index
    private String name;

    public Item() {
    }

    public Item(String name) {
        this.name = name;
    }

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
}
