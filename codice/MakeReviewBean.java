package com.example.demo;

//classe <<bean>>

public class MakeReviewBean  {

    //attributi
    private String videogame;
    private String user;
    private String text;
    private int score;

    //metodi
    public MakeReviewBean(String videogame, String user, String text, int score){
        this.videogame=videogame;
        this.user=user;
        this.text=text;
        this.score=score;
    }
    public String getVideogame(){
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
    public boolean validateText(){
        int len=this.text.length();
        return len >= 30 && len <= 500;
    }
    public boolean validateScore(){
        return this.score >= 0 && this.score <= 10;
    }

}
