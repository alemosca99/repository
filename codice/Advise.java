package com.example.demo;

import java.util.ArrayList;
import java.util.List;

//classe <<entity>>

public class Advise {

    //attributi
    int id;
    private User user;
    private String text;
    private List<AdviseResult> result;

    //metodi
    public Advise(int id, User user, String text){
        this.id=id;
        this.user=user;
        this.text=text;
        this.result = new ArrayList<>();
    }
    public Advise(int id, User user, String text, List<AdviseResult> result){
        this.id=id;
        this.user=user;
        this.text=text;
        this.result = new ArrayList<>(result);
    }
    public int getId() {
        return this.id;
    }
    public User getUser() {
        return this.user;
    }
    public String getText() {
        return this.text;
    }
    public List<AdviseResult> getResult(){
        return this.result;
    }

}
