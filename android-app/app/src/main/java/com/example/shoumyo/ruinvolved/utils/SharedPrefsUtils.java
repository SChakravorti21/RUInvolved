package com.example.shoumyo.ruinvolved.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class SharedPrefsUtils {

    private static final String AUTH_FILE = "auth_file";
    private static final String PREFS_FILE = "prefs_file";
    private static final String USERNAME_KEY = "user_name";
    private static final String IS_ADMIN_KEY = "is_admin";
    private static final String FAVORITED_CLUBS = "favorited_clubs";

    public static String getUsername(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(AUTH_FILE, Context.MODE_PRIVATE);
        return sharedPrefs.getString(USERNAME_KEY, null);
    }

    public static void setUsername(Context context, String username) {
        SharedPreferences sharedPrefs =
                context.getSharedPreferences(AUTH_FILE, Context.MODE_PRIVATE);
        sharedPrefs.edit().putString(USERNAME_KEY, username).apply();
    }

    public static void setIsAdmin(Context context, boolean isAdmin) {
        SharedPreferences sharedPrefs =
                context.getSharedPreferences(AUTH_FILE, Context.MODE_PRIVATE);
        sharedPrefs.edit().putBoolean(IS_ADMIN_KEY, isAdmin).apply();
    }

    public static boolean getIsAdmin(Context context) {
        return context.getSharedPreferences(AUTH_FILE, Context.MODE_PRIVATE)
                .getBoolean(IS_ADMIN_KEY, false);
    }

    public static Set<String> getFavoritedClubs(Context context) {

        SharedPreferences sharedPrefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);

        return sharedPrefs.getStringSet(FAVORITED_CLUBS, null);

    }


    public static boolean isFavorited(Context context, int clubId) {
        SharedPreferences sharedPrefs =
                context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);

        Set<String> favoritedClubs = sharedPrefs.getStringSet(FAVORITED_CLUBS, null);
        return favoritedClubs != null && favoritedClubs.contains("" + clubId);
    }


    public static void addFavoriteClub(Context context, int clubId) {
        SharedPreferences sharedPrefs =
                context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);

        Set<String> favoritedClubs = sharedPrefs.getStringSet(FAVORITED_CLUBS, null);

        if(favoritedClubs == null)
            favoritedClubs = new HashSet<>();
        favoritedClubs.add("" + clubId);

        sharedPrefs.edit().putStringSet(FAVORITED_CLUBS, favoritedClubs).apply();
    }

    public static void removeFavoriteClub(Context context, int clubId) {
        SharedPreferences sharedPrefs =
                context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);

        Set<String> favoritedClubs = sharedPrefs.getStringSet(FAVORITED_CLUBS, null);
        if(favoritedClubs == null) return;

        favoritedClubs.remove("" + clubId);
        sharedPrefs.edit().putStringSet(FAVORITED_CLUBS, favoritedClubs).apply();
    }
}
