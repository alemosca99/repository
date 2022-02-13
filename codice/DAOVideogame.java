package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//classe <<DAO>>

public class DAOVideogame {

    //attributi
    private static DAOVideogame daoVideogameInstance=null;
    private List<Videogame> videogames = new ArrayList<>();

    //metodi
    protected DAOVideogame() throws SQLException, ClassNotFoundException{
        dbGetAllVideogames();
    }
    public static DAOVideogame getDAOVideogameInstance() throws SQLException, ClassNotFoundException{
        if(DAOVideogame.daoVideogameInstance == null){
            DAOVideogame.daoVideogameInstance = new DAOVideogame();
        }
        return DAOVideogame.daoVideogameInstance;
    }
    public List<Videogame> getAllVideogames(){
        return  new ArrayList<>(this.videogames);
    }
    public Videogame getVideogameByName(String name){
        for(Videogame v : this.videogames){
            if(v.getName().equals(name)){
                return v;
            }
        }
        return null;
    }
    public void insertVideogame(Videogame v) throws SQLException, ClassNotFoundException{
        dbInsertVideogame(v);
        dbGetAllVideogames();
    }
    public void dbGetAllVideogames() throws SQLException, ClassNotFoundException{
        Statement smt=null;
        try{
            Connection conn=DBConnect.getDBConnectInstance().getConn();
            smt=conn.createStatement();
            String sql="SELECT * FROM videogames ORDER BY Year desc";
            ResultSet result=smt.executeQuery(sql);
            if(result.next()){
                do{
                    this.videogames.add(new Videogame(result.getString("Name"), result.getInt("Score"), result.getString("Genre"), result.getInt("Year")));
                }while(result.next());
            }
            result.close();
        }
        finally {
            if(smt != null){
                smt.close();
            }
        }
    }
    public void deleteVideogame(String videogame) throws SQLException, ClassNotFoundException{
        dbdeleteGame(videogame);
        dbGetAllVideogames();
    }
    public void dbInsertVideogame(Videogame v) throws SQLException, ClassNotFoundException{
        Statement smt=null;
        try{
            Connection conn=DBConnect.getDBConnectInstance().getConn();
            smt=conn.createStatement();
            //inserimento nuovo videogioco
            String sql="INSERT Videogames VALUES('" + v.getName() + "', " + v.getScore() + ", '" + v.getGenre() + "', " + v.getYear() + ")";
            smt.executeUpdate(sql);
        }
        finally {
            if(smt != null){
                smt.close();
            }
        }
    }
    public void updateScore(String videogame, int newScore) throws SQLException, ClassNotFoundException{
        dbupdateScore(videogame, newScore);
        DAOVideogame.getDAOVideogameInstance().getVideogameByName(videogame).setScore(newScore);
    }
    private void dbupdateScore(String videogame, int newScore) throws SQLException, ClassNotFoundException{
        Statement smt=null;
        try {
            Connection conn = DBConnect.getDBConnectInstance().getConn();
            smt= conn.createStatement();
            String sql = "UPDATE Videogames SET Score=" + newScore + " WHERE Name='" + videogame + "'";
            smt.executeUpdate(sql);
        }
        finally {
            if(smt != null){
                smt.close();
            }
        }
    }
    private void dbdeleteGame(String videogame) throws SQLException, ClassNotFoundException{
        Statement smt=null;
        try {
            Connection conn = DBConnect.getDBConnectInstance().getConn();
            smt= conn.createStatement();
            String sql = "DELETE FROM Videogames WHERE Name='" + videogame + "'";
            smt.executeUpdate(sql);
        }
        finally {
            if(smt != null){
                smt.close();
            }
        }
    }

}
