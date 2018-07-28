package com.tatar.shoppinglist.di.app.module;

import com.fatboyindustrial.gsonjodatime.DateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tatar.shoppinglist.data.network.ShoppingListAPI;
import com.tatar.shoppinglist.di.app.scope.AppScope;

import org.joda.time.DateTime;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class ShoppingListServiceModule {

    @AppScope
    @Provides
    public ShoppingListAPI shoppingListAPI(Retrofit retrofit) {
        return retrofit.create(ShoppingListAPI.class);
    }

    @AppScope
    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(ShoppingListAPI.BASE_URL)
                .build();
    }

    @AppScope
    @Provides
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeConverter());
        return gsonBuilder.create();
    }

}
