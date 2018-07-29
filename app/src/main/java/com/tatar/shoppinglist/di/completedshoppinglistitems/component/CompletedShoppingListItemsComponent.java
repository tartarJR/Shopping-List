package com.tatar.shoppinglist.di.completedshoppinglistitems.component;

import com.tatar.shoppinglist.di.completedshoppinglistitems.module.CompletedShoppingListItemsModule;
import com.tatar.shoppinglist.di.completedshoppinglistitems.scope.CompletedShoppingListItemsScope;
import com.tatar.shoppinglist.ui.completedshoppinglistitems.ItemDisplayActivity;

import dagger.Component;

@CompletedShoppingListItemsScope
@Component(modules = {CompletedShoppingListItemsModule.class})
public interface CompletedShoppingListItemsComponent {
    void inject(ItemDisplayActivity itemDisplayActivity);
}
