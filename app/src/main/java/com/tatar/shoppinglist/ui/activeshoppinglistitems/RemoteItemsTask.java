package com.tatar.shoppinglist.ui.activeshoppinglistitems;

import android.os.AsyncTask;

import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingListItem;
import com.tatar.shoppinglist.data.network.ShoppingListService;
import com.tatar.shoppinglist.data.network.model.RemoteShoppingList;
import com.tatar.shoppinglist.data.network.model.RemoteShoppingListItem;
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

        List<RemoteShoppingListItem> remoteRemoteShoppingListItems = new ArrayList<>();

        for (ShoppingListItem shoppingListItem : shoppingListItems) {
            RemoteShoppingListItem remoteRemoteShoppingListItem = new RemoteShoppingListItem();
            remoteRemoteShoppingListItem.setId(shoppingListItem.getId());
            remoteRemoteShoppingListItem.setName(shoppingListItem.getName());
            remoteRemoteShoppingListItem.setCollected(shoppingListItem.isCollected());
            remoteRemoteShoppingListItems.add(remoteRemoteShoppingListItem);
        }

        RemoteShoppingList remoteShoppingList = new RemoteShoppingList();
        remoteShoppingList.setId(strings[0]);
        remoteShoppingList.setName(strings[1]);
        remoteShoppingList.setRemoteShoppingListItems(remoteRemoteShoppingListItems);
        remoteShoppingList.setUserId(sharedPreferencesManager.getUserId());

        shoppingListService.postShoppingList(remoteShoppingList, postShoppingListCallbackImpl);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        itemDisplayView.toggleProgressBar();
        itemDisplayView.navigateToMainActivity();
    }
}
