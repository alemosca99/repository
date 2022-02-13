package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//classe <<DAO>>

public class DAOReview {

    //attributi
    private List<Review> reviews;
    private static DAOReview daoReviewInstance=null;

    //metodi
    private DAOReview() throws SQLException, ClassNotFoundException{
       this.reviews=dbgetAllReviews();
    }
    public static DAOReview getDAOReviewInstance() throws SQLException, ClassNotFoundException {
        if(DAOReview.daoReviewInstance == null){
            DAOReview.daoReviewInstance = new DAOReview();
        }
        return DAOReview.daoReviewInstance;
    }
    public List<Review> getAllReviews(){
        return this.reviews;
    }
    private List<Review> dbgetAllReviews() throws SQLException, ClassNotFoundException{
        Statement smt=null;
        try{
            //ricerca recensioni
            Connection conn=DBConnect.getDBConnectInstance().getConn();
            smt=conn.createStatement();
            String sql="SELECT * FROM Reviews ORDER BY Date desc";
            ResultSet result=smt.executeQuery(sql);
            //creazione lista recensioni
            List<Review> list = new ArrayList<>();
            if(result.next()){
                do{
                    Videogame v=DAOVideogame.getDAOVideogameInstance().getVideogameByName(result.getString("Videogame"));
                    User u=DAOUser.getDAOUserInstance().getUserByName(result.getString("User"));
                    list.add(new Review(v, u, result.getString("text"), result.getInt("score"), new Date(result.getDate("Date").getTime())));
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
    public List<Review> getReviewsByGame(String videogame){
        List<Review> list = new ArrayList<>();
        for(Review r: reviews){
            if(r.getVideogame().getName().equals(videogame)){
                list.add(r);
            }
        }
        return list;
    }
    public List<Review> getReviewsByUser(User u){
        List<Review> list = new ArrayList<>();
        for(Review r: reviews){
            if(r.getUser().getName().equals(u.getName())){
                list.add(r);
            }
        }
        return list;
    }
    public void insertReview(Review r) throws SQLException, ClassNotFoundException{
        dbinsertReview(r);
        this.reviews=dbgetAllReviews();
    }
    private void dbinsertReview(Review r) throws  SQLException, ClassNotFoundException{
        Statement smt=null;
        try{
            Connection conn=DBConnect.getDBConnectInstance().getConn();
            smt=conn.createStatement();
            java.sql.Date date=new Date(r.getDate().getTime());
            String sql="INSERT Reviews VALUES('" + r.getVideogame().getName() + "' ,'" + r.getUser().getName() + "', '" + r.getText() + "', " + r.getScore() + " ,'" + date + "')";
            smt.executeUpdate(sql);
        }
        finally {
            if(smt != null){
                smt.close();
            }
        }

    }
}
