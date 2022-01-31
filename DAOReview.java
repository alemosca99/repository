package com.example.demo;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class DAOReview {

    //attributes
    private List<Review> reviews;
    private static DAOReview DAOReviewInstance=null;

    //methods
    private DAOReview() throws Exception{
       this.reviews=DBgetAllReviews();
    }
    public static DAOReview getDAOReviewInstance() throws Exception{
        if(DAOReview.DAOReviewInstance == null){
            DAOReview.DAOReviewInstance = new DAOReview();
        }
        return DAOReview.DAOReviewInstance;
    }
    public List<Review> getAllReviews(){
        return this.reviews;
    }
    private List<Review> DBgetAllReviews() throws Exception{
        Statement smt=null;
        try{
            //ricerca recensioni
            Connection conn=DBConnect.getDBConnectInstance().getConn();
            smt=conn.createStatement();
            String sql="SELECT * FROM Reviews ORDER BY Date desc";
            ResultSet result=smt.executeQuery(sql);
            //creazione lista recensioni
            List<Review> list = new ArrayList<Review>();
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
    public List<Review> getReviewsByGame(Videogame v) throws Exception{
        List<Review> list = new ArrayList<>();
        for(Review r: reviews){
            if(r.getVideogame().getName().equals(v.getName())){
                list.add(r);
            }
        }
        return list;
    }
    public List<Review> getReviewsByUser(User u) throws Exception{
        List<Review> list = new ArrayList<>();
        for(Review r: reviews){
            if(r.getUser().getName() ==u.getName()){
                list.add(r);
            }
        }
        return list;
    }
    public void insertReview(Review r) throws Exception{
        DBinsertReview(r);
        this.reviews=DBgetAllReviews();
    }
    private void DBinsertReview(Review r) throws  Exception{
        Statement smt=null;
        try{
            Connection conn=DBConnect.getDBConnectInstance().getConn();
            smt=conn.createStatement();
            //ricerca duplicato
            for(Review rev : reviews){
                if(r.getVideogame().getName().equals(rev.getVideogame().getName()) && r.getUser().getName().equals(rev.getUser().getName())){
                    DuplicatedInstanceException e = new DuplicatedInstanceException();
                    throw e;
                }
            }
            //inserimento recensione
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
