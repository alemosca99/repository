package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOUser {

    //attributes
    List<User> users;
    List<String> admin;
    private static DAOUser DAOUserInstance=null;

    //methods
    private DAOUser() throws Exception{
        users=DBgetAllUsers();
    }
    public static DAOUser getDAOUserInstance() throws Exception{
        if(DAOUser.DAOUserInstance == null){
            DAOUser.DAOUserInstance = new DAOUser();
        }
        return DAOUser.DAOUserInstance;
    };
    public User getUserByName(String user) throws Exception{
        for(User u : this.users){
            if(u.getName().equals(user)){
                return u;
            }
        }
        return null;
    }
    public void insertUser(User u) throws Exception{
        DBinsertUser(u);
        users=DBgetAllUsers();
    }
    public void DBinsertUser(User u) throws Exception{
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
    private List<User> DBgetAllUsers() throws Exception {
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
            this.users = new ArrayList<User>();
            if (result.next()) {
                do {
                    Boolean isAdmin = false;
                    for (String admin : this.admin) {
                        if (admin.equals(result.getString("Name"))) {
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
