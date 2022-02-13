package com.example.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//classe <<DAO>>

public class DAOAdvise {

    //attributi
    private List<Advise> advice;
    private static DAOAdvise daoAdviseInstance=null;

    //metodi
    private DAOAdvise() throws SQLException, ClassNotFoundException{
        this.advice=dBgetAllAdvice();
    }
    public static DAOAdvise getDAOAdviseInstance() throws SQLException, ClassNotFoundException{
        if(DAOAdvise.daoAdviseInstance == null){
            DAOAdvise.daoAdviseInstance = new DAOAdvise();
        }
        return DAOAdvise.daoAdviseInstance;
    }
    public Advise getAdvise(String user, int id){
        for(Advise a : advice){
            if(a.getUser().getName().equals(user) && a.getId() == id){
                return a;
            }
        }
        return null;
    }
    public List<Advise> getAllAdvice(){
        return this.advice;
    }
    public List<Advise> getAdviceByUser(String user){
        List<Advise> list = new ArrayList<>();
        for(Advise a : advice){
            if(a.getUser().getName().equals(user)){
                list.add(a);
            }
        }
        return list;
    }
    public void insertAdvise(Advise a) throws SQLException, ClassNotFoundException{
        dBinsertAdvise(a);
        this.advice=dBgetAllAdvice();
    }
    public void deleteAdvise(String user, int id) throws SQLException, ClassNotFoundException{
        dbdeleteAdvise(user, id);
        dBgetAllAdvice();
    }
    private List<Advise> dBgetAllAdvice() throws SQLException, ClassNotFoundException {
        Statement smt=null;
        try{
            Connection conn=DBConnect.getDBConnectInstance().getConn();
            smt=conn.createStatement();
            //ricerca consigli
            String sql="SELECT * FROM Advice";
            ResultSet result=smt.executeQuery(sql);
            //creazione lista consigli
            List<Advise> list = new ArrayList<>();
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
    public void dBinsertAdvise(Advise a) throws SQLException, ClassNotFoundException{
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
    private void dbdeleteAdvise(String user, int id) throws SQLException, ClassNotFoundException{
        Statement smt=null;
        try {
            Connection conn = DBConnect.getDBConnectInstance().getConn();
            smt= conn.createStatement();
            String sql = "DELETE FROM Advice WHERE User='" + user + "' and id=" + id;
            smt.executeUpdate(sql);
        }
        finally {
            if(smt != null){
                smt.close();
            }
        }
    }

}
