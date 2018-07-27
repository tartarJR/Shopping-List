package com.tatar.shoppinglist.di.additem.component;

import com.tatar.shoppinglist.di.additem.module.AddItemActivityModule;
import com.tatar.shoppinglist.di.additem.scope.AddItemActivityScope;
import com.tatar.shoppinglist.di.app.component.AppComponent;
import com.tatar.shoppinglist.ui.shoppinglist.additem.AddItemActivity;

import dagger.Component;

@AddItemActivityScope
@Component(modules = {AddItemActivityModule.class}, dependencies = AppComponent.class)
public interface AddItemActivityComponent {
    void injectItemsActivity(AddItemActivity addItemActivity);
}
