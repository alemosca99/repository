package com.example.demo;

import java.io.Serializable;

public class Session implements Serializable {

    //attributi
    private String user;
    private boolean admin;

    //metodi
    public void setUser(String user){
        this.user=user;
    }
    public String getUser() {
        return user;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    public boolean isAdmin() {
        return admin;
    }

}
