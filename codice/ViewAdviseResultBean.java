package com.example.demo;

//classe <<bean>>

public class ViewAdviseResultBean {

    //attributi
    private String videogame;
    private int advice;

    //metodi
    public ViewAdviseResultBean(String videogame){
        this.videogame=videogame;
        this.advice=1;
    }
    public String getVideogame() {
        return this.videogame;
    }
    public void incrementAdvice(){
        this.advice++;
    }
    public int getAdvice() {
        return this.advice;
    }
}
