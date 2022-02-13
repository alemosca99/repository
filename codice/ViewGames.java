package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//classe <<controller>>

public class ViewGames {

    //metodi
    public List<ViewGamesBean> viewGames() throws SQLException, ClassNotFoundException, ItemNotFoundException{
        DAOVideogame d = new DAOVideogame();
        List<Videogame> list=d.getAllVideogames();
        if(list.isEmpty()){
            throw new ItemNotFoundException();
        }
        List<ViewGamesBean> listBean = new ArrayList<>() ;
        for(Videogame v: list){
            listBean.add(new ViewGamesBean(v.getName(), v.getScore(), v.getGenre(), v.getYear()));
        }
        return listBean;
    }

}
