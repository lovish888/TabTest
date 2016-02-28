package xyz.wheretopark.tabtest;

/**
 * Created by LovishJain on 2/27/2016.
 */
public class User {
    String name, email, password;
    public User(String name,String email,String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name,String password){
        this.name = name;
        this.email = "";
        this.password = password;
    }
}
