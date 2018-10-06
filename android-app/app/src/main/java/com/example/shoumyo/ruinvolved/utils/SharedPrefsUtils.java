package com.example.shoumyo.ruinvolved.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsUtils {

    private static final String AUTH_FILE = "auth_file";
    private static final String AUTH_TOKEN_KEY = "auth_token";

    public static void setAuthToken(Context context, String authToken) {
        SharedPreferences sharedPrefs =
                context.getSharedPreferences(AUTH_FILE, Context.MODE_PRIVATE);

        sharedPrefs.edit()
                .putString(AUTH_TOKEN_KEY, authToken)
                .apply();
    }

    public static String getAuthToken(Context context) {
        SharedPreferences sharedPrefs =
                context.getSharedPreferences(AUTH_FILE, Context.MODE_PRIVATE);

        return sharedPrefs.getString(AUTH_TOKEN_KEY, null);
    }
}
