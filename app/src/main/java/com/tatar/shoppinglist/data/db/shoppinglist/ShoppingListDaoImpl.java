package com.tatar.shoppinglist.data.db.shoppinglist;

import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;

public class ShoppingListDaoImpl implements ShoppingListDao {

    private final String TAG = ShoppingListDaoImpl.class.getSimpleName();

    @Override
    public String createShoppingList(final String name) {

        Realm realm = null;

        final String id = UUID.randomUUID().toString();

        try {
            realm = Realm.getDefaultInstance();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    ShoppingList shoppingList = realm.createObject(ShoppingList.class, id);
                    shoppingList.setName(name);
                }
            });

        } finally {
            if (realm != null) {
                realm.close();
            }
        }

        return id;
    }

    @Override
    public void updateShoppingList(String id, final String name) {
        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();

            final ShoppingList shoppingList = realm.where(ShoppingList.class).equalTo(ShoppingList.ID_FIELD, id).findFirst();

            if (shoppingList != null) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        shoppingList.setName(name);
                    }
                });
            }
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
    public void addItemToShoppingList(String id, String name) {
        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();

            ShoppingList shoppingList = realm.where(ShoppingList.class).equalTo(ShoppingList.ID_FIELD, id).findFirst();
            ShoppingListItem shoppingListItem = realm.createObject(ShoppingListItem.class, UUID.randomUUID().toString());
            shoppingListItem.setName(name);
            shoppingList.getShoppingListItems().add(shoppingListItem);

            /* TODO inject ItemDao or use createItem method here to add as an Item to app as well if there is no Item existing with the given name parameter */


        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public void removeItemFromShoppingList(String id, int position) {

    }
}
