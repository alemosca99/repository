package com.example.demo;

import java.util.Date;

//classe <<entity>>

public class Review {

    //attributi
    private Videogame videogame;
    private User user;
    private String text;
    private int score;
    private Date date;

    //metodi
    public Review(Videogame videogame, User user, String text, int score, Date date){
        this.videogame=videogame;
        this.user=user;
        this.text=text;
        this.score=score;
        this.date=date;
    }
    public Videogame getVideogame() {
        return videogame;
    }
    public User getUser() {
        return user;
    }
    public String getText() {
        return text;
    }
    public int getScore(){
        return this.score;
    }
    public Date getDate() {
        return date;
    }
}