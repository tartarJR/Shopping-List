package com.tatar.shoppinglist.di.component;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.di.module.ContextModule;
import com.tatar.shoppinglist.di.module.DaoModule;
import com.tatar.shoppinglist.di.scope.AppScope;

import dagger.Component;

@AppScope
@Component(modules = {ContextModule.class, DaoModule.class})
public interface AppComponent {

    ItemDao getItemDao();
}
