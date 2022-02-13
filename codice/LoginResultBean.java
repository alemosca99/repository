package com.example.demo;

//classe <<bean>>

public class LoginResultBean {

    //attributi
    String user;
    boolean admin;

    //metodi
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
