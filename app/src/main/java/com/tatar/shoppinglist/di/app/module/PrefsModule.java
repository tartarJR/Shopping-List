package com.tatar.shoppinglist.di.app.module;

import android.content.Context;

import com.tatar.shoppinglist.data.prefs.SharedPreferencesManager;
import com.tatar.shoppinglist.di.app.scope.AppScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class PrefsModule {

    @AppScope
    @Provides
    public SharedPreferencesManager sharedPreferencesManager(Context context) {
        return new SharedPreferencesManager(context);
    }

}
