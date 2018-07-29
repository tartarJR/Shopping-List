package com.tatar.shoppinglist.di.completedshoppinglist.component;

import com.tatar.shoppinglist.di.app.component.AppComponent;
import com.tatar.shoppinglist.di.completedshoppinglist.module.CompletedShoppingListsActivityModule;
import com.tatar.shoppinglist.di.completedshoppinglist.scope.CompletedShoppingListsActivityScope;
import com.tatar.shoppinglist.ui.completedshoppinglist.CompletedShoppingListsActivity;

import dagger.Component;

@CompletedShoppingListsActivityScope
@Component(modules = {CompletedShoppingListsActivityModule.class}, dependencies = AppComponent.class)
public interface CompletedShoppingListsActivityComponent {
    void injectCompletedShoppingListsActivity(CompletedShoppingListsActivity completedShoppingListsActivity);
}
