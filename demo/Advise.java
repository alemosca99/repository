package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class Advise {

    //attributes
    int id;
    private User user;
    private String text;
    private List<AdviseResult> result;

    //methods
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
        return id;
    }
    public User getUser() {
        return user;
    }
    public String getText() {
        return text;
    }

}
