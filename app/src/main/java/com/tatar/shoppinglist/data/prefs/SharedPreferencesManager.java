package com.tatar.shoppinglist.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String USER_PREF_NAME = "USER_PREF";
    private static final String PREF_KEY_USER_ID = "PREF_KEY_USER_ID";

    private final SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_PREF_NAME, Context.MODE_PRIVATE);
    }

    public String getUserId() {
        String userId = sharedPreferences.getString(PREF_KEY_USER_ID, "");
        return userId.equals("") ? "" : userId;
    }

    public void setUserId(String userId) {
        String currentUserId = sharedPreferences.getString(PREF_KEY_USER_ID, "");

        if (currentUserId.isEmpty()) {
            sharedPreferences.edit().putString(PREF_KEY_USER_ID, userId).apply();
        }
    }
}
