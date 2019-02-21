package com.example.nanhijaan;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SetLanguage {

    public static final String LANGUAGE = "language" ;

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
}