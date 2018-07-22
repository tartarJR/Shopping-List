package com.tatar.shoppinglist.data.db.shoppinglist;

import com.tatar.shoppinglist.data.db.item.model.Item;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;

public class ShoppingListDaoImpl implements ShoppingListDao {

    private final String TAG = ShoppingListDaoImpl.class.getSimpleName();

    @Override
    public void createShoppingList(final String name) {

        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    ShoppingList shoppingList = realm.createObject(ShoppingList.class, UUID.randomUUID().toString());
                    shoppingList.setName(name);
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public void deleteShoppingList(String id) {
        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();

            final ShoppingList shoppingList = realm.where(ShoppingList.class).equalTo(ShoppingList.ID_FIELD, id).findFirst();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (shoppingList != null) {
                        shoppingList.deleteFromRealm();
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
    public List<ShoppingList> getAllShoppingLists() {
        Realm realm = null;

        List<ShoppingList> shoppingLists = null;

        try {
            realm = Realm.getDefaultInstance();
            shoppingLists = realm.copyFromRealm(realm.where(ShoppingList.class).equalTo(ShoppingList.IS_COMPLETED_FIELD, false).findAll());
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

        return shoppingLists;
    }

    @Override
    public void addItemToShoppingList(String id, Item item) {

    }

    @Override
    public void removeItemFromShoppingList(String id, Item item) {

    }
}
