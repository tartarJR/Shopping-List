package com.tatar.shoppinglist.ui.completedshoppinglists;

import com.tatar.shoppinglist.data.network.ShoppingListService;
import com.tatar.shoppinglist.data.network.model.RemoteShoppingList;
import com.tatar.shoppinglist.data.prefs.SharedPreferencesManager;
import com.tatar.shoppinglist.utils.NetworkUtil;

import java.util.List;

import timber.log.Timber;

import static com.tatar.shoppinglist.data.network.ShoppingListService.GetShoppingListsCallback;
import static com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayPresenter;
import static com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayView;

public class ShoppingListDisplayPresenterImpl implements ShoppingListDisplayPresenter, GetShoppingListsCallback {

    private ShoppingListDisplayView shoppingListDisplayView;
    private ShoppingListService shoppingListService;
    private SharedPreferencesManager sharedPreferencesManager;
    private NetworkUtil networkUtil;

    public ShoppingListDisplayPresenterImpl(ShoppingListDisplayView shoppingListDisplayView, ShoppingListService shoppingListService, SharedPreferencesManager sharedPreferencesManager, NetworkUtil networkUtil) {
        this.shoppingListDisplayView = shoppingListDisplayView;
        this.shoppingListService = shoppingListService;
        this.sharedPreferencesManager = sharedPreferencesManager;
        this.networkUtil = networkUtil;
    }

    @Override
    public void loadCompletedShoppingLists() {
        try {
            if (networkUtil.isNetworkAvailable()) {
                shoppingListDisplayView.toggleProgressBar();
                shoppingListService.getShoppingLists(sharedPreferencesManager.getUserId(), this);
            } else {
                shoppingListDisplayView.navigateToMainActivity();
                shoppingListDisplayView.showNoInternetMessage();
            }
        } catch (Exception e) {
            Timber.e("loadCompletedShoppingLists: ", e);
            shoppingListDisplayView.toggleProgressBar();
            shoppingListDisplayView.showErrorMessage();
        }
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
        shoppingListDisplayView.showErrorMessage();
    }
}
