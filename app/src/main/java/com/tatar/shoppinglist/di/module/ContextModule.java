package com.tatar.shoppinglist.di.module;

import android.content.Context;

import com.tatar.shoppinglist.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @AppScope
    public Context context() {
        return context.getApplicationContext();
    }

}
