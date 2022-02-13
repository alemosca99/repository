package com.example.demo;

//classe <<bean>>

public class LoginBean {

    //attributi
    private String user;
    private String password;

    //metodi
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
