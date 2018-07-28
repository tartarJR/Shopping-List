package com.tatar.shoppinglist.data.network;

import com.tatar.shoppinglist.data.db.shoppinglist.model.ShoppingList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ShoppingListService {

    String BASE_URL = "";

    @GET("shoppinglists")
    Call<List<ShoppingList>> getCompletedShoppingLists();

    @POST("ShoppingListService")
    Call<List<ShoppingList>> postCompletedShoppingList();

}
