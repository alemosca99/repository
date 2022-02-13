package com.example.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//classe <<DAO>>

public class DAOAdviseResult {

    //attributi
    List<AdviseResult> adviceResults;
    private static DAOAdviseResult daoAdviseResultInstance=null;

    //metodi
    private DAOAdviseResult() throws SQLException, ClassNotFoundException{
        this.adviceResults=dBgetAllAdviceResults();
    }
    public static  DAOAdviseResult getDAOAdviseResultInstance() throws SQLException, ClassNotFoundException{
        if(DAOAdviseResult.daoAdviseResultInstance == null){
            DAOAdviseResult.daoAdviseResultInstance = new DAOAdviseResult();
        }
        return DAOAdviseResult.daoAdviseResultInstance;
    }
    public List<AdviseResult> getAdviceResultsByAdvise(String mainUser, int id){
        List<AdviseResult> list = new ArrayList<>();
        for(AdviseResult a : adviceResults){
            if(a.getMainUser().getName().equals(mainUser) && a.getId() == id){
                list.add(a);
            }
        }
        return list;
    }
    private List<AdviseResult> dBgetAllAdviceResults() throws SQLException, ClassNotFoundException{
        Statement smt=null;
        try{
            Connection conn=DBConnect.getDBConnectInstance().getConn();
            smt=conn.createStatement();
            //ricerca risulatati
            String sql="SELECT * FROM Advise";
            ResultSet result=smt.executeQuery(sql);
            //creazione lista risultati
            List<AdviseResult> list = new ArrayList<>();
            if(result.next()){
                do{
                    User mainUser=DAOUser.getDAOUserInstance().getUserByName(result.getString("MainUser"));
                    User otherUser=DAOUser.getDAOUserInstance().getUserByName(result.getString("OtherUser"));
                    Videogame videogame=DAOVideogame.getDAOVideogameInstance().getVideogameByName(result.getString("Videogame"));
                    list.add(new AdviseResult(mainUser, result.getInt("Id"), otherUser, videogame));
                }while(result.next());
            }
            return list;
        }
        finally {
            if(smt != null){
                smt.close();
            }
        }
    }
    public void insertAdviseResult(AdviseResult a) throws SQLException, ClassNotFoundException{
        Statement smt=null;
        try{
            Connection conn=DBConnect.getDBConnectInstance().getConn();
            smt=conn.createStatement();
            String sql="INSERT Advise VALUES(" + a.getId()  + ",'" + a.getMainUser().getName() + "', '" + a.getOtherUser().getName() + "','" + a.getVideogame().getName() + "')";
            smt.executeUpdate(sql);
        }
        finally {
            if(smt != null){
                smt.close();
            }
        }
        adviceResults=dBgetAllAdviceResults();
    }

}
