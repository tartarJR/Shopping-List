package com.tatar.shoppinglist;

import android.app.Activity;
import android.app.Application;

import com.tatar.shoppinglist.di.app.component.AppComponent;
import com.tatar.shoppinglist.di.app.component.DaggerAppComponent;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // init Timber for logging
        Timber.plant(new Timber.DebugTree());

        appComponent = DaggerAppComponent.builder().build();

        // initialize Realm
        Realm.init(getApplicationContext());

        // configure Realm for the app
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("shopping_list.realm")
                .build();

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
