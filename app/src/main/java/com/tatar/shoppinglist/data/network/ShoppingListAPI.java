package com.tatar.shoppinglist.data.network;


import com.tatar.shoppinglist.data.network.model.RemoteShoppingList;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShoppingListAPI {

    String BASE_URL = "https://shopping-list-53f01.firebaseio.com/";

    @GET("/lists.json?orderBy=\"user_id\"")
    Call<HashMap<String, RemoteShoppingList>> getCompletedShoppingLists(@Query("equalTo") String userId);

    @POST("/lists.json")
    Call<RemoteShoppingList> postCompletedShoppingList(@Body RemoteShoppingList remoteShoppingList);

}

