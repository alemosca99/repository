package com.example.demo;

//classe <<bean>>

public class MakeAdviseResultBean {

    //attributi
    private String user;
    private int adviceId;
    private String adviceUser;
    private String videogame;

    //metodi
    public MakeAdviseResultBean(String user, int adviceId, String adviceUser, String videogame){
        this.user=user;
        this.adviceId=adviceId;
        this.adviceUser=adviceUser;
        this.videogame=videogame;
    }
    public String getUser() {
        return user;
    }
    public int getAdviceId() {
        return adviceId;
    }
    public String getAdviceUser() {
        return adviceUser;
    }
    public String getVideogame() {
        return videogame;
    }

}
