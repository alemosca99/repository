package com.example.demo;

//classe <<entity>>

public class Videogame{

    //attributi
    private String name;
    private int score;
    private String genre;
    private int year;

    //metodi
    public Videogame(String name, int score, String genre, int year){
        this.name=name;
        this.score=score;
        this.genre=genre;
        this.year=year;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public String getName(){
        return this.name;
    }
    public int getScore() {
        return this.score;
    }
    public String getGenre(){
        return this.genre;
    }
    public int getYear() {
        return this.year;
    }

}