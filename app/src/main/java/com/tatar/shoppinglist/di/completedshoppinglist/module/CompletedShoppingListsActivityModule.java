package com.tatar.shoppinglist.di.completedshoppinglist.module;

import com.tatar.shoppinglist.data.network.ShoppingListAPI;
import com.tatar.shoppinglist.data.network.ShoppingListService;
import com.tatar.shoppinglist.di.completedshoppinglist.scope.CompletedShoppingListsActivityScope;
import com.tatar.shoppinglist.ui.completedshoppinglist.CompletedGetShoppingListsPresenterImpl;
import com.tatar.shoppinglist.ui.completedshoppinglist.CompletedShoppingListAdapter;
import com.tatar.shoppinglist.ui.completedshoppinglist.CompletedShoppingListsActivity;

import dagger.Module;
import dagger.Provides;

import static com.tatar.shoppinglist.ui.completedshoppinglist.CompletedShoppingListsContract.CompletedShoppingListsPresenter;
import static com.tatar.shoppinglist.ui.completedshoppinglist.CompletedShoppingListsContract.CompletedShoppingListsView;

@Module
public class CompletedShoppingListsActivityModule {

    private CompletedShoppingListsView completedShoppingListsView;
    private CompletedShoppingListsActivity completedShoppingListsActivity;

    public CompletedShoppingListsActivityModule(CompletedShoppingListsView completedShoppingListsView, CompletedShoppingListsActivity completedShoppingListsActivity) {
        this.completedShoppingListsView = completedShoppingListsView;
        this.completedShoppingListsActivity = completedShoppingListsActivity;
    }

    @CompletedShoppingListsActivityScope
    @Provides
    public CompletedShoppingListsActivity completedShoppingListsActivity() {
        return completedShoppingListsActivity;
    }

    @CompletedShoppingListsActivityScope
    @Provides
    public CompletedShoppingListsView completedShoppingListsView() {
        return completedShoppingListsView;
    }

    @CompletedShoppingListsActivityScope
    @Provides
    public CompletedShoppingListsPresenter CompletedShoppingListsPresenter(CompletedShoppingListsView completedShoppingListsView, ShoppingListService shoppingListService) {
        return new CompletedGetShoppingListsPresenterImpl(completedShoppingListsView, shoppingListService);
    }

    @CompletedShoppingListsActivityScope
    @Provides
    public ShoppingListService shoppingListService(ShoppingListAPI shoppingListAPI) {
        return new ShoppingListService(shoppingListAPI);
    }

    @CompletedShoppingListsActivityScope
    @Provides
    public CompletedShoppingListAdapter completedShoppingListAdapter() {
        return new CompletedShoppingListAdapter();
    }
}
