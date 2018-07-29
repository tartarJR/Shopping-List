package com.tatar.shoppinglist.di.activeshoppinglists.component;

import com.tatar.shoppinglist.di.activeshoppinglists.module.ActiveShoppingListsModule;
import com.tatar.shoppinglist.di.activeshoppinglists.scope.ActiveShoppingListsScope;
import com.tatar.shoppinglist.di.app.component.AppComponent;
import com.tatar.shoppinglist.ui.activeshoppinglists.ShoppingListDisplayActivity;

import dagger.Component;

@ActiveShoppingListsScope
@Component(modules = {ActiveShoppingListsModule.class}, dependencies = AppComponent.class)
public interface ActiveShoppingListsComponent {
    void inject(ShoppingListDisplayActivity shoppingListDisplayActivity);
}
