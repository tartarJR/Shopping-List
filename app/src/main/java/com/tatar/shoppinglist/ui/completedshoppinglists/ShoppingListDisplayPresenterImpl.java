package com.tatar.shoppinglist.ui.completedshoppinglists;

import com.tatar.shoppinglist.data.network.ShoppingListService;
import com.tatar.shoppinglist.data.network.model.RemoteShoppingList;
import com.tatar.shoppinglist.data.prefs.SharedPreferencesManager;

import java.util.List;

import timber.log.Timber;

import static com.tatar.shoppinglist.data.network.ShoppingListService.GetShoppingListsCallback;
import static com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayPresenter;
import static com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayView;

public class ShoppingListDisplayPresenterImpl implements ShoppingListDisplayPresenter, GetShoppingListsCallback {

    private ShoppingListDisplayView shoppingListDisplayView;
    private ShoppingListService shoppingListService;
    private SharedPreferencesManager sharedPreferencesManager;

    public ShoppingListDisplayPresenterImpl(ShoppingListDisplayView shoppingListDisplayView, ShoppingListService shoppingListService, SharedPreferencesManager sharedPreferencesManager) {
        this.shoppingListDisplayView = shoppingListDisplayView;
        this.shoppingListService = shoppingListService;
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    @Override
    public void loadCompletedShoppingLists(String userId) {
        shoppingListDisplayView.toggleProgressBar();

        try {
            shoppingListService.getShoppingLists(userId, this);
        } catch (Exception e) {
            Timber.e("loadCompletedShoppingLists: ", e);
            shoppingListDisplayView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public String getUserId() {
        return sharedPreferencesManager.getUserId();
    }

    @Override
    public void onDataReceived(List<RemoteShoppingList> remoteShoppingLists) {
        shoppingListDisplayView.toggleProgressBar();
        shoppingListDisplayView.toggleNoDataTv(remoteShoppingLists.size());
        shoppingListDisplayView.displayCompletedShoppingLists(remoteShoppingLists);
    }

    @Override
    public void onFailure() {
        shoppingListDisplayView.toggleProgressBar();
        shoppingListDisplayView.displayMessage("An error occurred, please try again later.");
    }
}
