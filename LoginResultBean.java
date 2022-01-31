package com.example.demo;

public class LoginResultBean {

    //attributes
    String user;
    boolean admin;

    //methods
    public LoginResultBean(String user, boolean admin){
        this.user=user;
        this.admin=admin;
    }
    public String getUser() {
        return user;
    }
    public boolean isAdmin() {
        return admin;
    }

}
