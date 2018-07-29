package com.tatar.shoppinglist.ui.activeshoppinglistitems;

import android.os.AsyncTask;

import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem;
import com.tatar.shoppinglist.data.network.ShoppingListService;
import com.tatar.shoppinglist.data.network.model.ShoppingList;
import com.tatar.shoppinglist.data.prefs.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class RemoteItemsTask extends AsyncTask<String, Void, Void> {

    private ItemDisplayContract.ItemDisplayView itemDisplayView;
    private ShoppingListDao shoppingListDao;
    private ShoppingListService shoppingListService;
    private SharedPreferencesManager sharedPreferencesManager;
    private ItemDisplayPresenterImpl postShoppingListCallbackImpl;

    public RemoteItemsTask(ItemDisplayContract.ItemDisplayView itemDisplayView, ShoppingListDao shoppingListDao, ShoppingListService shoppingListService, SharedPreferencesManager sharedPreferencesManager, ItemDisplayPresenterImpl postShoppingListCallbackImpl) {
        this.itemDisplayView = itemDisplayView;
        this.shoppingListDao = shoppingListDao;
        this.shoppingListService = shoppingListService;
        this.sharedPreferencesManager = sharedPreferencesManager;
        this.postShoppingListCallbackImpl = postShoppingListCallbackImpl;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        itemDisplayView.toggleProgressBar();
    }

    @Override
    protected Void doInBackground(String... strings) {
        shoppingListDao.updateShoppingListCompleted(strings[0], true);

        List<ShoppingListItem> shoppingListItems = shoppingListDao.getShoppingListItemsById(strings[0]);

        List<com.tatar.shoppinglist.data.network.model.ShoppingListItem> remoteShoppingListItems = new ArrayList<>();

        for (com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem shoppingListItem : shoppingListItems) {
            com.tatar.shoppinglist.data.network.model.ShoppingListItem remoteShoppingListItem = new com.tatar.shoppinglist.data.network.model.ShoppingListItem();
            remoteShoppingListItem.setId(shoppingListItem.getId());
            remoteShoppingListItem.setName(shoppingListItem.getName());
            remoteShoppingListItem.setCollected(shoppingListItem.isCollected());
            remoteShoppingListItems.add(remoteShoppingListItem);
        }

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setId(strings[0]);
        shoppingList.setName(strings[1]);
        shoppingList.setShoppingListItems(remoteShoppingListItems);
        shoppingList.setUserId(sharedPreferencesManager.getUserId());

        shoppingListService.postShoppingList(shoppingList, postShoppingListCallbackImpl);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        itemDisplayView.toggleProgressBar();
        itemDisplayView.navigateToMainActivity();
    }
}
