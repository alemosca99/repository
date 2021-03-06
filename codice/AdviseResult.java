package com.example.demo;

//classe <<entity>>

public class AdviseResult {

    //attributi
    private User mainUser;
    private int id;
    private User otherUser;
    private Videogame videogame;

    //metodi
    public AdviseResult(User mainUser, int id, User otherUser, Videogame videogame){
        this.mainUser=mainUser;
        this.id=id;
        this.otherUser=otherUser;
        this.videogame=videogame;
    }
    public User getMainUser() {
        return mainUser;
    }
    public int getId() {
        return id;
    }
    public User getOtherUser() {
        return otherUser;
    }
    public Videogame getVideogame() {
        return this.videogame;
    }

}
