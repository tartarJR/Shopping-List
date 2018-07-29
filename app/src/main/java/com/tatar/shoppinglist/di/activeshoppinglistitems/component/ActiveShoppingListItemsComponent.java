package com.tatar.shoppinglist.di.activeshoppinglistitems.component;

import com.tatar.shoppinglist.di.activeshoppinglistitems.module.ActiveShoppingListItemsModule;
import com.tatar.shoppinglist.di.activeshoppinglistitems.scope.ActiveShoppingListItemsScope;
import com.tatar.shoppinglist.di.app.component.AppComponent;
import com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemDisplayActivity;

import dagger.Component;

@ActiveShoppingListItemsScope
@Component(modules = {ActiveShoppingListItemsModule.class}, dependencies = AppComponent.class)
public interface ActiveShoppingListItemsComponent {
    void inject(ItemDisplayActivity itemDisplayActivity);
}
