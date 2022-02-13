package com.example.demo;

import java.sql.SQLException;
import java.util.List;

//classe <<controller>>

public class InsertGame {

    //metodi
    public void insertGame(InsertGameBean b) throws DuplicatedInstanceException, SQLException, ClassNotFoundException {
        //ricerca duplicato
        List<Videogame> list = DAOVideogame.getDAOVideogameInstance().getAllVideogames();
        for(Videogame v : list){
            if(b.getName().equals(v.getName())){
                throw new DuplicatedInstanceException();
            }
        }
        //inserimento
        Videogame v = new Videogame(b.getName(), -1, b.getGenre(), b.getYear());
        DAOVideogame.getDAOVideogameInstance().insertVideogame(v);
    }

}
