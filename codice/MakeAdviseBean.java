package com.example.demo;

//classe <<bean>>

public class MakeAdviseBean {

    //attributi
    private String user;
    private String text;

    //metodi
    public MakeAdviseBean(String user, String text){
        this.user=user;
        this.text=text;
    }
    public String getUser() {
        return user;
    }
    public String getText() {
        return text;
    }
    public boolean validateText(){
        int len=this.text.length();
        return len >= 30 && len <= 200;
    }
}
