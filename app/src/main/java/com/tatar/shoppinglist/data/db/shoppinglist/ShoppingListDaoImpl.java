package com.tatar.shoppinglist.data.db.shoppinglist;

import com.tatar.shoppinglist.data.db.item.model.Item;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;

public class ShoppingListDaoImpl implements ShoppingListDao {

    private final String TAG = ShoppingListDaoImpl.class.getSimpleName();

    @Override
    public List<ShoppingList> getAllShoppingLists() {
        Realm realm = null;

        List<ShoppingList> shoppingLists;

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
    public List<ShoppingListItem> getShoppingListItemsById(String shoppingListId) {
        Realm realm = null;

        List<ShoppingListItem> shoppingListItems = new ArrayList<>();

        try {
            realm = Realm.getDefaultInstance();
            ShoppingList shoppingList = realm.copyFromRealm(realm.where(ShoppingList.class).equalTo(ShoppingList.ID_FIELD, shoppingListId).findFirst());
            shoppingListItems.addAll(shoppingList.getShoppingListItems());
        } finally {
            if (realm != null) {
                realm.close();
            }
        }

        return shoppingListItems;
    }

    @Override
    public String createShoppingList(final String name) {

        Realm realm = null;

        final String shoppingListId = UUID.randomUUID().toString();

        try {
            realm = Realm.getDefaultInstance();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    ShoppingList shoppingList = realm.createObject(ShoppingList.class, shoppingListId);
                    shoppingList.setName(name);
                    shoppingList.setCompleted(false);
                }
            });

        } finally {
            if (realm != null) {
                realm.close();
            }
        }

        return shoppingListId;
    }

    @Override
    public void updateShoppingListName(String shoppingListId, final String name) {
        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();

            final ShoppingList shoppingList = realm.where(ShoppingList.class).equalTo(ShoppingList.ID_FIELD, shoppingListId).findFirst();

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
    public void updateShoppingListCompleted(String shoppingListId, final boolean isCompleted) {
        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();

            final ShoppingList shoppingList = realm.where(ShoppingList.class).equalTo(ShoppingList.ID_FIELD, shoppingListId).findFirst();

            if (shoppingList != null) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        shoppingList.setCompleted(isCompleted);
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
    public void deleteShoppingList(String shoppingListId) {
        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();

            final ShoppingList shoppingList = realm.where(ShoppingList.class).equalTo(ShoppingList.ID_FIELD, shoppingListId).findFirst();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (shoppingList != null) {

                        List<ShoppingListItem> itemsToDelete = new ArrayList<>(shoppingList.getShoppingListItems());

                        for (ShoppingListItem item : itemsToDelete) {
                            item.deleteFromRealm();
                        }

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
    public void addItemToShoppingList(final String shoppingListId, final String name) {

        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();

            // Add given Item to db if it is not existing
            // Avoid creating an item with an existing item name.
            final Item item = realm.where(Item.class).equalTo(Item.NAME_FIELD, name).findFirst();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (item == null) {
                        Item item = realm.createObject(Item.class, UUID.randomUUID().toString());
                        item.setName(name);
                    }

                    ShoppingList shoppingList = realm.where(ShoppingList.class).equalTo(ShoppingList.ID_FIELD, shoppingListId).findFirst();
                    ShoppingListItem shoppingListItem = realm.createObject(ShoppingListItem.class, UUID.randomUUID().toString());
                    shoppingListItem.setName(name);
                    shoppingListItem.setCollected(false);
                    shoppingList.getShoppingListItems().add(shoppingListItem);
                }
            });

        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public void removeItemFromShoppingList(String shoppingListId, final int position) {
        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();

            final ShoppingList shoppingList = realm.where(ShoppingList.class).equalTo(ShoppingList.ID_FIELD, shoppingListId).findFirst();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (shoppingList != null) {
                        ShoppingListItem shoppingListItem = shoppingList.getShoppingListItems().get(position);
                        if (shoppingListItem != null) {
                            shoppingListItem.deleteFromRealm();
                        }
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
    public void updateIsCollectedForItem(String itemId, final boolean isCollected) {
        Realm realm = null;

        try {
            realm = Realm.getDefaultInstance();

            final ShoppingListItem shoppingListItem = realm.where(ShoppingListItem.class).equalTo(ShoppingListItem.ID_FIELD, itemId).findFirst();

            if (shoppingListItem != null) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        shoppingListItem.setCollected(isCollected);
                    }
                });
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }
}
