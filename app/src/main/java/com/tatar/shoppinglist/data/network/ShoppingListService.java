package com.tatar.shoppinglist.data.network;

import android.support.annotation.NonNull;

import com.tatar.shoppinglist.data.network.model.ShoppingList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ShoppingListService {

    private ShoppingListAPI shoppingListAPI;

    public ShoppingListService(ShoppingListAPI shoppingListAPI) {
        this.shoppingListAPI = shoppingListAPI;
    }

    public void getShoppingLists(String userId, final GetShoppingListsCallback getShoppingListsCallback) {
        /*
        Added quotes around parameter because Firebase expects it like that.
        Couldn't find a way to way to solve it via Retrofit. Ugly work around tho.
        */
        Call<HashMap<String, ShoppingList>> call = shoppingListAPI.getCompletedShoppingLists("\"" + userId + "\"");
        call.enqueue(new Callback<HashMap<String, ShoppingList>>() {
            @Override
            public void onResponse(Call<HashMap<String, ShoppingList>> call, @NonNull Response<HashMap<String, ShoppingList>> response) {
                if (response.isSuccessful()) {
                    List<ShoppingList> shoppingLists = new ArrayList<>(response.body().values());
                    getShoppingListsCallback.onDataReceived(shoppingLists);
                } else {
                    getShoppingListsCallback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, ShoppingList>> call, Throwable t) {
                Timber.e("Unable to make GET request to API: " + t.getMessage());
            }
        });
    }

    public void postShoppingList(ShoppingList shoppingList, final PostShoppingListCallback postShoppingListCallback) {
        Call<ShoppingList> call = shoppingListAPI.postCompletedShoppingList(shoppingList);
        call.enqueue(new Callback<ShoppingList>() {
            @Override
            public void onResponse(Call<ShoppingList> call, Response<ShoppingList> response) {
                if (response.isSuccessful()) {
                    postShoppingListCallback.onPostSuccess();
                    Timber.d("POST request is successful: " + response.body().toString());
                } else {
                    postShoppingListCallback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ShoppingList> call, Throwable t) {
                postShoppingListCallback.onFailure();
                Timber.e("Unable to make POST request to API: " + t.getMessage());
            }
        });
    }

    public interface ShoppingListCallback {
        void onFailure();
    }

    public interface GetShoppingListsCallback extends ShoppingListCallback {
        void onDataReceived(List<ShoppingList> shoppingLists);
    }

    public interface PostShoppingListCallback extends ShoppingListCallback {
        void onPostSuccess();
    }
}
