package com.example.passwordmanager;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_ACCOUNT_CREATED = "isAccountCreated";
    private static final String MASTERPASSWORD_KEY = "masterPasswordKey";


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String masterPassword) {

        editor.putBoolean(IS_ACCOUNT_CREATED, true);
        editor.putString(MASTERPASSWORD_KEY, masterPassword);
        editor.commit();
    }

    public String getMasterPassword() {
        return sharedPreferences.getString(MASTERPASSWORD_KEY, null);
    }

    public boolean checkAccountCreated() {

        if (sharedPreferences.getBoolean(IS_ACCOUNT_CREATED, false)) {
            return true;
        } else {
            return false;
        }
    }

    public void logOutFromSession() {
        editor.clear();
        editor.commit();
    }


}
