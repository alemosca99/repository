package com.example.demo;

//classe <<bean>>

public class ViewAdviseResultInputBean {

    //attributi
    private String user;
    private int id;

    //metodi
    public ViewAdviseResultInputBean(String user, int id){
        this.user=user;
        this.id=id;
    }
    public String getUser() {
        return user;
    }
    public int getId() {
        return id;
    }

}
