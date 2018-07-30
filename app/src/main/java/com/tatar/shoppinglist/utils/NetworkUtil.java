package com.tatar.shoppinglist.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    private Context context;

    public NetworkUtil(Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        boolean isNetworkAvailable = false;

        if (networkInfo != null && networkInfo.isAvailable()) {
            isNetworkAvailable = true;
        }

        return isNetworkAvailable;
    }

}
