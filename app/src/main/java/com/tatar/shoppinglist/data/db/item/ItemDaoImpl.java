package com.tatar.shoppinglist.data.db.item;

import com.tatar.shoppinglist.data.db.item.model.Item;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;

public class ItemDaoImpl implements ItemDao {
    @Override
    public void createItem(final String name) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Item item1 = realm.createObject(Item.class, UUID.randomUUID().toString());
                item1.setName(name);
            }
        });

        realm.close();
    }

    @Override
    public void updateItem(String id, String name) {

    }

    @Override
    public void deleteItem(String id) {

    }

    @Override
    public Item getItem(String id) {
        return null;
    }

    @Override
    public List<Item> getAllItems() {
        Realm realm = Realm.getDefaultInstance();
        List<Item> gitHubUserList = realm.copyFromRealm(realm.where(Item.class).findAll());
        realm.close();

        return gitHubUserList;
    }
}
