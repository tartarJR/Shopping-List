package com.tatar.shoppinglist.di.app.component;

import com.tatar.shoppinglist.data.db.item.ItemDao;
import com.tatar.shoppinglist.data.db.shoppinglist.ShoppingListDao;
import com.tatar.shoppinglist.data.network.ShoppingListAPI;
import com.tatar.shoppinglist.data.prefs.SharedPreferencesManager;
import com.tatar.shoppinglist.di.app.module.ContextModule;
import com.tatar.shoppinglist.di.app.module.DaoModule;
import com.tatar.shoppinglist.di.app.module.PrefsModule;
import com.tatar.shoppinglist.di.app.module.ShoppingListServiceModule;
import com.tatar.shoppinglist.di.app.scope.AppScope;
import com.tatar.shoppinglist.utils.NetworkUtil;

import dagger.Component;

@AppScope
@Component(modules = {ContextModule.class, DaoModule.class, ShoppingListServiceModule.class, PrefsModule.class})
public interface AppComponent {
    ItemDao getItemDao();

    ShoppingListDao getShoppingListDao();

    ShoppingListAPI shoppingListAPI();

    SharedPreferencesManager sharedPreferencesManager();

    NetworkUtil networkUtil();
}
