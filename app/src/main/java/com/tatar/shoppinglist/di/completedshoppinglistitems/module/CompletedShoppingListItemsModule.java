package com.tatar.shoppinglist.di.completedshoppinglistitems.module;

import com.tatar.shoppinglist.di.completedshoppinglistitems.scope.CompletedShoppingListItemsScope;
import com.tatar.shoppinglist.ui.completedshoppinglistitems.ItemsAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class CompletedShoppingListItemsModule {

    @CompletedShoppingListItemsScope
    @Provides
    public ItemsAdapter itemsAdapter() {
        return new ItemsAdapter();
    }

}
