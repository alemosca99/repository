package com.example.demo;

//classe <<bean>>

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
        return len >= 5 && len <= 50;
    }
    public boolean validateGenre(){
        if(this.genre.equals("platform")){
            return true;
        }
        if(this.genre.equals("action")){
            return true;
        }
        if(this.genre.equals("shooter")){
            return true;
        }
        if(this.genre.equals("figther game")){
            return true;
        }
        if(this.genre.equals("survival horror")){
            return true;
        }
        return this.genre.equals("life simulator");
    }
    public boolean validateYear(){
        return this.year >= 1980;
    }

}
