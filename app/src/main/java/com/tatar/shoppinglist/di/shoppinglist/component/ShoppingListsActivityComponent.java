package com.tatar.shoppinglist.di.shoppinglist.component;

import com.tatar.shoppinglist.di.app.component.AppComponent;
import com.tatar.shoppinglist.di.shoppinglist.module.ShoppingListsActivityModule;
import com.tatar.shoppinglist.di.shoppinglist.scope.ShoppingListsActivityScope;
import com.tatar.shoppinglist.ui.shoppinglist.ShoppingListsActivity;

import dagger.Component;

@ShoppingListsActivityScope
@Component(modules = {ShoppingListsActivityModule.class}, dependencies = AppComponent.class)
public interface ShoppingListsActivityComponent {
    void injectShoppingListsActivity(ShoppingListsActivity shoppingListsActivity);
}
