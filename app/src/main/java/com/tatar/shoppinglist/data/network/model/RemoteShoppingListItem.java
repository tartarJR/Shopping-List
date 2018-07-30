package com.tatar.shoppinglist.data.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteShoppingListItem implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("is_collected")
    @Expose
    private boolean isCollected;

    public RemoteShoppingListItem() {

    }

    protected RemoteShoppingListItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        isCollected = in.readByte() != 0;
    }

    public static final Creator<RemoteShoppingListItem> CREATOR = new Creator<RemoteShoppingListItem>() {
        @Override
        public RemoteShoppingListItem createFromParcel(Parcel in) {
            return new RemoteShoppingListItem(in);
        }

        @Override
        public RemoteShoppingListItem[] newArray(int size) {
            return new RemoteShoppingListItem[size];
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

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeByte((byte) (isCollected ? 1 : 0));
    }
}
