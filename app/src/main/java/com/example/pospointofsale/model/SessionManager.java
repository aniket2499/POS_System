package com.example.pospointofsale.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public  SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences("AppKey",0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public  void setUserDetails(String userEmail){
        editor.putString("KEY_USEREMAIL",userEmail);
        editor.commit();
    }

    public String getUserDetails(){
        return sharedPreferences.getString("KEY_USEREMAIL","");
    }
}
