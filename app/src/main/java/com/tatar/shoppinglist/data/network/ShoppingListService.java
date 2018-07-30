package com.tatar.shoppinglist.data.network;

import android.support.annotation.NonNull;

import com.tatar.shoppinglist.data.network.model.RemoteShoppingList;

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
        Call<HashMap<String, RemoteShoppingList>> call = shoppingListAPI.getCompletedShoppingLists("\"" + userId + "\"");
        call.enqueue(new Callback<HashMap<String, RemoteShoppingList>>() {
            @Override
            public void onResponse(Call<HashMap<String, RemoteShoppingList>> call, @NonNull Response<HashMap<String, RemoteShoppingList>> response) {
                if (response.isSuccessful()) {
                    List<RemoteShoppingList> remoteShoppingLists = new ArrayList<>(response.body().values());
                    getShoppingListsCallback.onDataReceived(remoteShoppingLists);
                } else {
                    getShoppingListsCallback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, RemoteShoppingList>> call, Throwable t) {
                Timber.e("Unable to make GET request to API: " + t.getMessage());
            }
        });
    }

    public void postShoppingList(RemoteShoppingList remoteShoppingList, final PostShoppingListCallback postShoppingListCallback) {
        Call<RemoteShoppingList> call = shoppingListAPI.postCompletedShoppingList(remoteShoppingList);
        call.enqueue(new Callback<RemoteShoppingList>() {
            @Override
            public void onResponse(Call<RemoteShoppingList> call, Response<RemoteShoppingList> response) {
                if (response.isSuccessful()) {
                    postShoppingListCallback.onPostSuccess();
                    Timber.d("POST request is successful: " + response.body().toString());
                } else {
                    postShoppingListCallback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<RemoteShoppingList> call, Throwable t) {
                postShoppingListCallback.onFailure();
                Timber.e("Unable to make POST request to API: " + t.getMessage());
            }
        });
    }

    public interface ShoppingListCallback {
        void onFailure();
    }

    public interface GetShoppingListsCallback extends ShoppingListCallback {
        void onDataReceived(List<RemoteShoppingList> remoteShoppingLists);
    }

    public interface PostShoppingListCallback extends ShoppingListCallback {
        void onPostSuccess();
    }
}
