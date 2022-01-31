package com.example.demo;

import java.sql.*;

public class DBConnect {

    //attributes
    private static DBConnect DBConnectInstance=null;
    private Connection conn;

    //methods
    protected DBConnect() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.conn=DriverManager.getConnection("jdbc:mysql://localhost:3307/Gameville", "root", "1Geografia");
    }
    public static DBConnect getDBConnectInstance() throws Exception{
        if(DBConnect.DBConnectInstance == null){
            DBConnect.DBConnectInstance = new DBConnect();
        }
        return DBConnect.DBConnectInstance;
    }
    public Connection getConn(){
        return this.conn;
    }

}
