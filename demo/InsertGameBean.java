package com.example.demo;

public class InsertGameBean {

    //attributi
    private String name;
    private String genre;
    private int year;

    //metodi
    public InsertGameBean(String name, String genre, int year){
        this.name=name;
        this.genre=genre;
        this.year=year;
    }
    public String getName() {
        return name;
    }
    public String getGenre() {
        return genre;
    }
    public int getYear() {
        return year;
    }
    public boolean validateName(){
        int len=this.name.length();
        if(len >= 5 && len <= 50){
            return true;
        }
        return false;
    }
    public boolean validateYear(){
        if(this.year >= 1980){
            return true;
        }
        return false;
    }

}
