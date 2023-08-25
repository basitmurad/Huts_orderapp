package com.example.huts;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "UserSession";
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NUMBER = "number";
    private static final String HUT_NAME = "hutName";
    private static final String HUT_IMAGEURI = "hutImage";


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(HUT_NAME, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(HUT_IMAGEURI, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences = context.getSharedPreferences(KEY_NUMBER , Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();
    }

    public void saveCredentials(String name, String password, String email ,String number) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NUMBER ,number);
        editor.apply();
    }

    public String getNaame() {
        return sharedPreferences.getString(KEY_NAME, "");
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getNumber()
    {
        return sharedPreferences.getString(KEY_NUMBER,"");
    }





    public void saveHutName(String hutName) {
        editor.putString(HUT_NAME, hutName);
        editor.apply();
    }

    public String getHutName() {
        return sharedPreferences.getString(HUT_NAME, "");
    }

    public void saveHutImage(String hutName) {
        editor.putString(HUT_IMAGEURI, hutName);
        editor.apply();
    }

    public String getHutImage() {
        return sharedPreferences.getString(HUT_IMAGEURI, "");
    }
}
