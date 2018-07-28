package com.tatar.shoppinglist.data.network;


import com.tatar.shoppinglist.data.network.model.ShoppingList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ShoppingListAPI {

    String BASE_URL = "https://shopping-list-53f01.firebaseio.com/";

    @GET("/lists.json")
    Call<List<ShoppingList>> getCompletedShoppingLists();

    @POST("/lists.json")
    Call<ShoppingList> postCompletedShoppingList(@Body ShoppingList shoppingList);

}

