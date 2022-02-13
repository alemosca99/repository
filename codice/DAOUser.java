package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//classe <<DAO>>

public class DAOUser {

    //attributi
    List<User> users;
    List<String> admin;
    private static DAOUser daoUserInstance=null;

    //metodi
    private DAOUser() throws SQLException, ClassNotFoundException{
        users=dbGetAllUsers();
    }
    public static DAOUser getDAOUserInstance() throws SQLException, ClassNotFoundException{
        if(DAOUser.daoUserInstance == null){
            DAOUser.daoUserInstance = new DAOUser();
        }
        return DAOUser.daoUserInstance;
    }
    public User getUserByName(String user){
        for(User u : this.users){
            if(u.getName().equals(user)){
                return u;
            }
        }
        return null;
    }
    public void insertUser(User u) throws SQLException, ClassNotFoundException{
        dbInsertUser(u);
        users=dbGetAllUsers();
    }
    public void dbInsertUser(User u) throws ClassNotFoundException, SQLException{
        Statement smt=null;
        try{
            Connection conn=DBConnect.getDBConnectInstance().getConn();
            smt=conn.createStatement();
            String sql;
            sql="INSERT Users VALUES('" + u.getName() + "', '" + u.getPassword() + "', '" + u.getEmail() + "')";
            smt.executeUpdate(sql);
        }
        finally {
            if(smt != null){
                smt.close();
            }
        }
    }
    public List<User> getAllUsers(){
        return this.users;
    }
    private List<User> dbGetAllUsers() throws SQLException, ClassNotFoundException {
        Statement smt = null;
        try {
            Connection conn = DBConnect.getDBConnectInstance().getConn();
            smt = conn.createStatement();
            //ricerca admin
            String sql = "SELECT * FROM Admin";
            ResultSet result = smt.executeQuery(sql);
            //creazione lista admin
            this.admin = new ArrayList<>();
            if (result.next()) {
                do {
                    this.admin.add(result.getString("Name"));
                } while (result.next());
            }
            //ricerca utenti
            sql = "SELECT * FROM Users";
            result = smt.executeQuery(sql);
            //creazione lista utenti
            this.users = new ArrayList<>();
            if (result.next()) {
                do {
                    Boolean isAdmin = false;
                    for (String ad : this.admin) {
                        if (ad.equals(result.getString("Name"))) {
                            isAdmin = true;
                        }
                    }
                    User u = new User(result.getString("Name"), result.getString("Password"), result.getString("Email"), isAdmin);
                    users.add(u);
                } while (result.next());
            }
            return users;
        }
        finally {
            if (smt != null) {
                smt.close();
            }
        }
    }

}
