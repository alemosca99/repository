package com.example.demo;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOAdvise {

    //attributes
    private List<Advise> advice;
    private static DAOAdvise DAOAdviseInstance=null;

    //methods
    private DAOAdvise() throws Exception{
        this.advice=DBgetAllAdvice();
    }
    public static DAOAdvise getDAOAdviseInstance() throws Exception{
        if(DAOAdvise.DAOAdviseInstance == null){
            DAOAdvise.DAOAdviseInstance = new DAOAdvise();
        }
        return DAOAdvise.DAOAdviseInstance;
    }
    public List<Advise> getAllAdvice(){
        return this.advice;
    }
    public List<Advise> getAdviceByUser(String user) throws Exception{
        List<Advise> list = new ArrayList<>();
        for(Advise a : advice){
            if(a.getUser().getName().equals(user)){
                list.add(a);
            }
        }
        return list;
    }
    public Advise getAdvise(String user, int id) throws Exception{
        for(Advise a : advice){
            if(a.getUser().getName() == user && a.getId() == id){
                return a;
            }
        }
        Exception e = new ItemNotFoundException();
        throw e;
    }
    public void insertAdvise(Advise a) throws Exception{
        DBinsertAdvise(a);
        this.advice=DBgetAllAdvice();
    }
    private List<Advise> DBgetAllAdvice() throws Exception{
        Statement smt=null;
        try{
            Connection conn=DBConnect.getDBConnectInstance().getConn();
            smt=conn.createStatement();
            //ricerca consigli
            String sql="SELECT * FROM Advice";
            ResultSet result=smt.executeQuery(sql);
            //creazione lista consigli
            List<Advise> list = new ArrayList<Advise>();
            if(result.next()){
                do{
                    User u=DAOUser.getDAOUserInstance().getUserByName(result.getString("User"));
                    int id=result.getInt("Id");
                    List<AdviseResult> resultList=DAOAdviseResult.getDAOAdviseResultInstance().getAdviceResultsByAdvise(u.getName(), id);
                    list.add(new Advise(id, u, result.getString("Text"), resultList));
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
    public void DBinsertAdvise(Advise a) throws Exception{
        Statement smt=null;
        try{
            Connection conn=DBConnect.getDBConnectInstance().getConn();
            smt=conn.createStatement();
            String sql="INSERT Advice VALUES(" + a.getId() +", '" + a.getUser().getName() + "','" + a.getText() + "')";
            smt.executeUpdate(sql);
        }
        finally {
            if(smt != null){
                smt.close();
            }
        }
    }
    public int maxId(String user) {
        int i = 0;
        for (Advise a : advice) {
            if(a.getUser().getName().equals(user)) {
                i++;
            }
        }
        return i + 1;
    }

}
