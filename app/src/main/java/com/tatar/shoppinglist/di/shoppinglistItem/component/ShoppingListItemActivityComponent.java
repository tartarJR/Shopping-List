package com.tatar.shoppinglist.di.shoppinglistItem.component;

import com.tatar.shoppinglist.di.shoppinglistItem.module.ShoppingListItemActivityModule;
import com.tatar.shoppinglist.di.shoppinglistItem.scope.ShoppingListItemActivityScope;
import com.tatar.shoppinglist.di.app.component.AppComponent;
import com.tatar.shoppinglist.ui.shoppinglistitem.ShoppingListItemActivity;

import dagger.Component;

@ShoppingListItemActivityScope
@Component(modules = {ShoppingListItemActivityModule.class}, dependencies = AppComponent.class)
public interface ShoppingListItemActivityComponent {
    void injectItemsActivity(ShoppingListItemActivity shoppingListItemActivity);
}
