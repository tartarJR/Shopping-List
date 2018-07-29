package com.tatar.shoppinglist.di.completedshoppinglists.module;

import com.tatar.shoppinglist.data.network.ShoppingListAPI;
import com.tatar.shoppinglist.data.network.ShoppingListService;
import com.tatar.shoppinglist.di.completedshoppinglists.scope.CompletedShoppingListsScope;
import com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListAdapter;
import com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListDisplayActivity;
import com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListDisplayPresenterImpl;

import dagger.Module;
import dagger.Provides;

import static com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayPresenter;
import static com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListDisplayContract.ShoppingListDisplayView;

@Module
public class CompletedShoppingListsModule {

    private ShoppingListDisplayView shoppingListDisplayView;
    private ShoppingListDisplayActivity shoppingListDisplayActivity;

    public CompletedShoppingListsModule(ShoppingListDisplayView shoppingListDisplayView, ShoppingListDisplayActivity shoppingListDisplayActivity) {
        this.shoppingListDisplayView = shoppingListDisplayView;
        this.shoppingListDisplayActivity = shoppingListDisplayActivity;
    }

    @CompletedShoppingListsScope
    @Provides
    public ShoppingListDisplayActivity shoppingListDisplayActivity() {
        return shoppingListDisplayActivity;
    }

    @CompletedShoppingListsScope
    @Provides
    public ShoppingListDisplayView shoppingListDisplayView() {
        return shoppingListDisplayView;
    }

    @CompletedShoppingListsScope
    @Provides
    public ShoppingListDisplayPresenter shoppingListDisplayPresenter(ShoppingListDisplayView shoppingListDisplayView, ShoppingListService shoppingListService) {
        return new ShoppingListDisplayPresenterImpl(shoppingListDisplayView, shoppingListService);
    }

    @CompletedShoppingListsScope
    @Provides
    public ShoppingListService shoppingListService(ShoppingListAPI shoppingListAPI) {
        return new ShoppingListService(shoppingListAPI);
    }

    @CompletedShoppingListsScope
    @Provides
    public ShoppingListAdapter shoppingListAdapter() {
        return new ShoppingListAdapter();
    }
}
