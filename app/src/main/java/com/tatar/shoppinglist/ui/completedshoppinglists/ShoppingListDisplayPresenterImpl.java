package com.tatar.shoppinglist.ui.completedshoppinglists;

import com.tatar.shoppinglist.data.network.ShoppingListService;
import com.tatar.shoppinglist.data.network.model.ShoppingList;

import java.util.List;

import timber.log.Timber;

import static com.tatar.shoppinglist.data.network.ShoppingListService.GetShoppingListsCallback;
import static com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayPresenter;
import static com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayView;

public class ShoppingListDisplayPresenterImpl implements ShoppingListDisplayPresenter, GetShoppingListsCallback {

    private ShoppingListDisplayView shoppingListDisplayView;
    private ShoppingListService shoppingListService;

    public ShoppingListDisplayPresenterImpl(ShoppingListDisplayView shoppingListDisplayView, ShoppingListService shoppingListService) {
        this.shoppingListDisplayView = shoppingListDisplayView;
        this.shoppingListService = shoppingListService;
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
    public void onDataReceived(List<ShoppingList> shoppingLists) {
        shoppingListDisplayView.toggleProgressBar();
        shoppingListDisplayView.toggleNoDataTv(shoppingLists.size());
        shoppingListDisplayView.displayCompletedShoppingLists(shoppingLists);
    }

    @Override
    public void onFailure() {
        shoppingListDisplayView.toggleProgressBar();
        shoppingListDisplayView.displayMessage("An error occurred, please try again later.");
    }
}
