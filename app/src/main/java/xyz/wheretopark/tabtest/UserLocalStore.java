package xyz.wheretopark.tabtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by LovishJain on 2/27/2016.
 */
public class UserLocalStore {
    public static final String FileName = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(FileName,0);
        //TODO check why it is done
        // The value "0" here is used for mode: There are several modes through which wee can
        //control the access of sharedPreference file like MODE_PRIVATE etc.
    }

    public void storeUserData(User user){
        SharedPreferences.Editor myEditor = userLocalDatabase.edit();
        myEditor.putString("name", user.name);
        myEditor.putString("email", user.email);
        myEditor.putString("password", user.password);
        myEditor.apply();
    }

    public User getLoggedInUser(){
        String name = userLocalDatabase.getString("name", "");
        String email = userLocalDatabase.getString("email","");
        String password = userLocalDatabase.getString("password","");

        User storedUser = new User(name,email,password);
        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor myEditor = userLocalDatabase.edit();
        myEditor.putBoolean("loggedIn",loggedIn);
        myEditor.apply();
    }

    public boolean getIfUserLoggedIn(){
        return userLocalDatabase.getBoolean("loggedIn",false);
    }

    public void clearUserData(){
        SharedPreferences.Editor myEditor = userLocalDatabase.edit();
        myEditor.clear();
        myEditor.apply();
    }
}
