package com.tatar.shoppinglist;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize Realm
        Realm.init(getApplicationContext());

        // configure Realm for the app
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("shopping_list.realm")
                .build();

        // make the above configuration default for Realm
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
