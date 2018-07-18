package com.tatar.shoppinglist.di.item.component;

import com.tatar.shoppinglist.di.app.component.AppComponent;
import com.tatar.shoppinglist.di.item.module.ItemAlertDialogModule;
import com.tatar.shoppinglist.di.item.module.ItemsModule;
import com.tatar.shoppinglist.di.item.scope.ItemsActivityScope;
import com.tatar.shoppinglist.ui.item.ItemsActivity;

import dagger.Component;

@ItemsActivityScope
@Component(modules = {ItemsModule.class, ItemAlertDialogModule.class}, dependencies = AppComponent.class)
public interface ItemsActivityComponent {
    void injectItemsActivity(ItemsActivity itemsActivity);
}
