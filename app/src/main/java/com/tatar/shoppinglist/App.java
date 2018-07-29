package com.tatar.shoppinglist;

import android.app.Activity;
import android.app.Application;

import com.tatar.shoppinglist.data.prefs.SharedPreferencesManager;
import com.tatar.shoppinglist.di.app.component.AppComponent;
import com.tatar.shoppinglist.di.app.component.DaggerAppComponent;
import com.tatar.shoppinglist.di.app.module.ContextModule;
import com.tatar.shoppinglist.utils.StringUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class App extends Application {

    private AppComponent appComponent;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    public void onCreate() {
        super.onCreate();

        // init Timber for logging
        Timber.plant(new Timber.DebugTree());

        appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        // initialize Realm
        Realm.init(getApplicationContext());

        // configure Realm for the app
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("shopping_list.realm")
                .build();

        /*
         * Generate and set a fake user id.
         * Just a work around for authentication since I did not have time to implement a real authentication via Firebase.
         */
        sharedPreferencesManager = appComponent.sharedPreferencesManager();
        sharedPreferencesManager.setUserId(StringUtils.generateFakeUserId());

        // make the above configuration default for Realm
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static App get(Activity activity) {
        return (App) activity.getApplication();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
