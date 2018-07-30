package com.tatar.shoppinglist.data.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RemoteShoppingList implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("shopping_list_items")
    @Expose
    private List<RemoteShoppingListItem> remoteShoppingListItems;

    @SerializedName("user_id")
    @Expose
    private String userId;

    public RemoteShoppingList() {

    }

    protected RemoteShoppingList(Parcel in) {
        id = in.readString();
        name = in.readString();
        remoteShoppingListItems = in.createTypedArrayList(RemoteShoppingListItem.CREATOR);
        userId = in.readString();
    }

    public static final Creator<RemoteShoppingList> CREATOR = new Creator<RemoteShoppingList>() {
        @Override
        public RemoteShoppingList createFromParcel(Parcel in) {
            return new RemoteShoppingList(in);
        }

        @Override
        public RemoteShoppingList[] newArray(int size) {
            return new RemoteShoppingList[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RemoteShoppingListItem> getRemoteShoppingListItems() {
        return remoteShoppingListItems;
    }

    public void setRemoteShoppingListItems(List<RemoteShoppingListItem> remoteShoppingListItems) {
        this.remoteShoppingListItems = remoteShoppingListItems;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeTypedList(remoteShoppingListItems);
        parcel.writeString(userId);
    }
}
