package com.tatar.shoppinglist.di.component;

import com.tatar.shoppinglist.di.module.ItemDialogModule;
import com.tatar.shoppinglist.di.scope.ActivityScope;
import com.tatar.shoppinglist.ui.item.ItemsActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ItemDialogModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    void injectItemsActivity(ItemsActivity itemsActivity);
}
