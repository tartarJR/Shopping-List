package com.tatar.shoppinglist.di.manageitems.component;

import com.tatar.shoppinglist.di.app.component.AppComponent;
import com.tatar.shoppinglist.di.manageitems.module.ManageItemsModule;
import com.tatar.shoppinglist.di.manageitems.scope.ManageItemsScope;
import com.tatar.shoppinglist.ui.manageitems.ItemsActivity;

import dagger.Component;

@ManageItemsScope
@Component(modules = {ManageItemsModule.class}, dependencies = AppComponent.class)
public interface ManageItemsComponent {
    void inject(ItemsActivity itemsActivity);
}
