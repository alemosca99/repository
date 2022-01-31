package com.example.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOAdviseResult {

    //attributes
    List<AdviseResult> adviceResults;
    private static DAOAdviseResult DAOAdviseResultInstance=null;

    //methods
    private DAOAdviseResult() throws Exception{
        this.adviceResults=DBgetAllAdviceResults();
    }
    public static  DAOAdviseResult getDAOAdviseResultInstance() throws Exception{
        if(DAOAdviseResult.DAOAdviseResultInstance == null){
            DAOAdviseResult.DAOAdviseResultInstance = new DAOAdviseResult();
        }
        return DAOAdviseResult.DAOAdviseResultInstance;
    }
    public List<AdviseResult> getAllAdviceResults() throws  Exception{
        return this.adviceResults;
    }
    public List<AdviseResult> getAdviceResultsByAdvise(String mainUser, int id) throws  Exception{
        List<AdviseResult> list = new ArrayList<>();
        for(AdviseResult a : adviceResults){
            if(a.getMainUser().getName().equals(mainUser) && a.getId() == id){
                list.add(a);
            }
        }
        return list;
    }
    private List<AdviseResult> DBgetAllAdviceResults() throws Exception{
        Statement smt=null;
        try{
            Connection conn=DBConnect.getDBConnectInstance().getConn();
            smt=conn.createStatement();
            //ricerca risulatati
            String sql="SELECT * FROM Advise";
            ResultSet result=smt.executeQuery(sql);
            //creazione lista risultati
            List<AdviseResult> list = new ArrayList<AdviseResult>();
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
    public void insertAdviseResult(AdviseResult a) throws Exception{
        //ricerca duplicato
        for(AdviseResult ar : adviceResults){
            if(a.getMainUser().getName().equals(ar.getMainUser().getName()) && a.getId() == ar.getId() && a.getOtherUser().getName().equals(ar.getOtherUser().getName())){
                DuplicatedInstanceException e = new DuplicatedInstanceException();
                throw e;
            }
        }
        //inserimento nuovo risultato consiglio
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
        adviceResults=DBgetAllAdviceResults();
    }

}
