package com.example.forest_app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.forest_app.form.AuthForm;
import com.google.gson.Gson;

public class LocalDatabase {

    private static LocalDatabase instance;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private static final String PREF_NAME = "local_database";

    private LocalDatabase(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static synchronized LocalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new LocalDatabase(context.getApplicationContext());
        }
        return instance;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void putAuthForm(String key, AuthForm form) {
        String json = gson.toJson(form);
        putString(key, json);
    }

    public AuthForm getAuthForm(String key) {
        String json = getString(key, null);
        if (json != null) {
            return gson.fromJson(json, AuthForm.class);
        }
        return null;
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
