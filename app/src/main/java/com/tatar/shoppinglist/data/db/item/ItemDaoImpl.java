package com.tatar.shoppinglist.data.db.item;

import com.tatar.shoppinglist.data.db.item.model.Item;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;

/**
 * A class to perform CRUD operations for Items through Realm local database.
 */
public class ItemDaoImpl implements ItemDao {

    private final String TAG = ItemDaoImpl.class.getSimpleName();

    @Override
    public boolean createItem(final String name) {

        boolean isCreated = false;

        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();

            // Avoid creating an item with an existing item name.
            final Item item = realm.where(Item.class).equalTo(Item.NAME_FIELD, name).findFirst();

            if (item == null) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Item item = realm.createObject(Item.class, UUID.randomUUID().toString());
                        item.setName(name);
                    }
                });

                isCreated = true;
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

        return isCreated;
    }

    @Override
    public boolean updateItem(String id, final String name) {

        boolean isUpdated = false;

        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();

            // Avoid updating an item name with an existing item name.
            final Item item = realm.where(Item.class).equalTo(Item.ID_FIELD, id).findFirst();

            if (item != null && !item.getName().equalsIgnoreCase(name)) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        item.setName(name);
                    }
                });

                isUpdated = true;
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

        return isUpdated;
    }

    @Override
    public void deleteItem(String id) {

        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();

            final Item item = realm.where(Item.class).equalTo(Item.ID_FIELD, id).findFirst();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (item != null) {
                        item.deleteFromRealm();
                    }
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public List<Item> getAllItems() {

        Realm realm = null;

        List<Item> gitHubUserList = null;

        try {
            realm = Realm.getDefaultInstance();
            gitHubUserList = realm.copyFromRealm(realm.where(Item.class).findAll());
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

        return gitHubUserList;
    }

}
