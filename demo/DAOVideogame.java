package com.example.demo;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOVideogame {

    //attributes
    private static DAOVideogame DAOVideogameInstance=null;
    private List<Videogame> videogames;

    //methods
    protected DAOVideogame() throws Exception{
        this.videogames=DBgetAllVideogames();
    };
    public static DAOVideogame getDAOVideogameInstance() throws Exception{
        if(DAOVideogame.DAOVideogameInstance == null){
            DAOVideogame.DAOVideogameInstance = new DAOVideogame();
        }
        return DAOVideogame.DAOVideogameInstance;
    }
    public List<Videogame> getAllVideogames() throws Exception{
        List<Videogame> list = new ArrayList<>(this.videogames);
        return list;
    }
    public Videogame getVideogameByName(String name) throws Exception{
        for(Videogame v : this.videogames){
            if(v.getName().equals(name)){
                return v;
            }
        }
        Exception e = new ItemNotFoundException();
        throw e;
    }
    public void insertVideogame(Videogame v) throws Exception{
        DBinsertVideogame(v);
        this.videogames=DBgetAllVideogames();
    }
    public List<Videogame> DBgetAllVideogames() throws Exception{
        Statement smt=null;
        try{
            Connection conn=DBConnect.getDBConnectInstance().getConn();
            smt=conn.createStatement();
            String sql="SELECT * FROM videogames ORDER BY Year desc";
            ResultSet result=smt.executeQuery(sql);
            List<Videogame> list = new ArrayList<Videogame>();
            if(result.next()){
                do{
                    list.add(new Videogame(result.getString("Name"), result.getInt("Score"), result.getString("Genre"), result.getInt("Year")));
                }while(result.next());
            }
            result.close();
            return list;
        }
        finally {
            if(smt != null){
                smt.close();
            }
        }
    }
    public void DBinsertVideogame(Videogame v) throws Exception{
        Statement smt=null;
        try{
            Connection conn=DBConnect.getDBConnectInstance().getConn();
            smt=conn.createStatement();
            //ricerca duplicato
            for(Videogame game : videogames ){
                if(v.getName().equals(game.getName())){
                    DuplicatedInstanceException e = new DuplicatedInstanceException();
                    throw e;
                }
            }
            //inserimento nuovo videogioco
            String sql="INSERT Videogames VALUES('" + v.getName() + " ', " + v.getScore() + ", '" + v.getGenre() + "', " + v.getYear() + ")";
            smt.executeUpdate(sql);
        }
        finally {
            if(smt != null){
                smt.close();
            }
        }
    }
    public void updateScore(String videogame, int newScore) throws Exception{
        DBupdateScore(videogame, newScore);
        DAOVideogame.getDAOVideogameInstance().getVideogameByName(videogame).setScore(newScore);
    }
    private void DBupdateScore(String videogame, int newScore) throws Exception{
        Statement smt=null;
        try {
            Connection conn = DBConnect.getDBConnectInstance().getConn();
            smt= conn.createStatement();;
            String sql = "UPDATE Videogames SET Score=" + newScore + " WHERE Name='" + videogame + "'";
            smt.executeUpdate(sql);
        }
        finally {
            if(smt != null){
                smt.close();
            }
        }
    }

}
