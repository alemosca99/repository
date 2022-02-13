package com.example.demo;

import java.util.Date;

//classe <<bean>>

public class ViewReviewsBean {

    //attributi
    private String videogame;
    private String user;
    private String text;
    private int score;
    private Date date;

    //metodi
    public ViewReviewsBean(String videogame, String user, String text, int score, Date date){
        this.videogame=videogame;
        this.user=user;
        this.text=text;
        this.score=score;
        this.date=date;
    }
    public String getVideogame() {
        return videogame;
    }
    public String getUser() {
        return user;
    }
    public String getText() {
        return text;
    }
    public int getScore() {
        return score;
    }
    public Date getDate() {
        return date;
    }

}
