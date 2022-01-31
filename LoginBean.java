package com.example.demo;

public class LoginBean {

    //attributes
    private String user;
    private String password;

    //methods
    public LoginBean(String user, String password){
        this.user=user;
        this.password=password;
    }
    public String getUser() {
        return user;
    }
    public String getPassword() {
        return password;
    }

}
