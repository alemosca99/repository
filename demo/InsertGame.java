package com.example.demo;

public class InsertGame {

    //metodi
    public void insertGame(InsertGameBean b) throws Exception{
        Videogame v = new Videogame(b.getName(), -1, b.getGenre(), b.getYear());
        DAOVideogame.getDAOVideogameInstance().insertVideogame(v);
    }

}
