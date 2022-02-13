package com.example.demo;

//classe <<entity>>

public class User {

    //attributi
    private String name;
    private String password;
    private String email;
    private boolean admin;

    //metodi
    public User(String name, String password, String email, boolean admin){
        this.name=name;
        this.password=password;
        this.email=email;
        this.admin=admin;
    }
    public String getName(){
        return this.name;
    }
    public String getPassword() {
        return this.password;
    }
    public String getEmail() {
        return this.email;
    }
    public boolean isAdmin() {
        return admin;
    }
}
