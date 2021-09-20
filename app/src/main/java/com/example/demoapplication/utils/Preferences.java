package com.example.demoapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class Preferences {
    public static final String PREFERENCES_KEY="DMSDriver";

    public static void setPreferenceValue(Context context, String key,
                                          String value) {

        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static void setPreferenceValue(Context context, String key,
                                          boolean value) {

        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public static void setPreferenceValueLong(Context context, String key,
                                          long value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, value);
        editor.commit();
    }
    public static void setPreferenceValue(Context context, String key, int value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setPreferenceValueSet(Context context, String key,
                                         Set<String> value) {

        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(key, value);
        editor.commit();
    }


    public static String getPreferenceValue(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY,
                Context.MODE_PRIVATE);

        return prefs.getString(key, "NA");

    }
    public static boolean getPreferenceValueBoolean(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY,
                Context.MODE_PRIVATE);

        return prefs.getBoolean(key, false);

    }
    public static long getPreferenceValueLong(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY,
                Context.MODE_PRIVATE);

        return prefs.getLong(key, 0);

    }
    public static Set<String> getPreferenceValueSet(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY,
                Context.MODE_PRIVATE);

        return prefs.getStringSet(key,null);

    }


    public static int getPreferenceValueInt(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY,
                Context.MODE_PRIVATE);

        return prefs.getInt(key, 0);

    }

    public static void getClearPrefs(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

    }
}