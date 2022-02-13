package com.example.demo;

//classe <<bean>>

public class ViewGamesBean {

    //attributi
    private String name;
    private int score;
    private String genre;
    private int year;

    //metodi
    public ViewGamesBean(String name, int score, String genre, int year){
        this.name=name;
        this.score=score;
        this.genre=genre;
        this.year=year;
    }
    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }
    public String getGenre() {
        return genre;
    }
    public int getYear() {
        return year;
    }
}
