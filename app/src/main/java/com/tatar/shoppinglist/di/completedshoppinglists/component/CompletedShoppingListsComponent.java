package com.tatar.shoppinglist.di.completedshoppinglists.component;

import com.tatar.shoppinglist.di.app.component.AppComponent;
import com.tatar.shoppinglist.di.completedshoppinglists.module.CompletedShoppingListsModule;
import com.tatar.shoppinglist.di.completedshoppinglists.scope.CompletedShoppingListsScope;
import com.tatar.shoppinglist.ui.completedshoppinglists.ShoppingListDisplayActivity;

import dagger.Component;

@CompletedShoppingListsScope
@Component(modules = {CompletedShoppingListsModule.class}, dependencies = AppComponent.class)
public interface CompletedShoppingListsComponent {
    void inject(ShoppingListDisplayActivity shoppingListDisplayActivity);
}
