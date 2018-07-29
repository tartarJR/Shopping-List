package com.tatar.shoppinglist.ui.completedshoppinglist;

import com.tatar.shoppinglist.data.network.ShoppingListService;
import com.tatar.shoppinglist.data.network.model.ShoppingList;

import java.util.List;

import timber.log.Timber;

import static com.tatar.shoppinglist.ui.completedshoppinglist.CompletedShoppingListsContract.CompletedShoppingListsPresenter;
import static com.tatar.shoppinglist.ui.completedshoppinglist.CompletedShoppingListsContract.CompletedShoppingListsView;

public class CompletedGetShoppingListsPresenterImpl implements CompletedShoppingListsPresenter, ShoppingListService.GetShoppingListsCallback {

    private CompletedShoppingListsView completedShoppingListsView;
    private ShoppingListService shoppingListService;

    public CompletedGetShoppingListsPresenterImpl(CompletedShoppingListsView completedShoppingListsView, ShoppingListService shoppingListService) {
        this.completedShoppingListsView = completedShoppingListsView;
        this.shoppingListService = shoppingListService;
    }

    @Override
    public void loadCompletedShoppingLists(String userId) {
        completedShoppingListsView.toggleProgressBar();

        try {
            shoppingListService.getShoppingLists(userId, this); // TODO try catch ?
        } catch (Exception e) {
            Timber.e("loadCompletedShoppingLists: ", e);
            completedShoppingListsView.displayMessage("An error occurred, please try again later.");
        }
    }

    @Override
    public void onDataReceived(List<ShoppingList> shoppingLists) {
        completedShoppingListsView.toggleProgressBar();
        completedShoppingListsView.toggleNoDataTv(shoppingLists.size());
        completedShoppingListsView.displayCompletedShoppingLists(shoppingLists);
    }

    @Override
    public void onFailure() {
        completedShoppingListsView.toggleProgressBar();
        completedShoppingListsView.displayMessage("An error occurred, please try again later.");
    }
}
