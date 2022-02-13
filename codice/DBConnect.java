package com.example.demo;

import java.sql.*;

public class DBConnect {

    //attributi
    private static DBConnect dbConnectInstance=null;
    private Connection conn;

    //metodi
    protected DBConnect() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn=DriverManager.getConnection("jdbc:mysql://localhost:3307/Gameville", "root", "1Geografia");
    }
    public static DBConnect getDBConnectInstance() throws ClassNotFoundException, SQLException{
        if(DBConnect.dbConnectInstance == null){
            DBConnect.dbConnectInstance = new DBConnect();
        }
        return DBConnect.dbConnectInstance;
    }
    public Connection getConn(){
        return this.conn;
    }

}
