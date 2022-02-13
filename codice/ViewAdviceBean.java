package com.example.demo;

//classe <<bean>>

public class ViewAdviceBean {

    //attributi
    private int id;
    private String user;
    private String text;

    //metodi
    public ViewAdviceBean(int id, String user, String text){
        this.id=id;
        this.text=text;
        this.user=user;
    }
    public int getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public String getUser() {
        return user;
    }

}
