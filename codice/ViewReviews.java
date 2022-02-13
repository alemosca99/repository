package com.example.demo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//classe <<controller>>

public class ViewReviews {

    //metodi
    public List<ViewReviewsBean> viewAllReviews() throws SQLException, ClassNotFoundException, ItemNotFoundException{
        List<Review> list=DAOReview.getDAOReviewInstance().getAllReviews();
        if(list.isEmpty()){
            throw new ItemNotFoundException();
        }
        List<ViewReviewsBean> lb = new ArrayList<>();
        for(Review r : list){
            lb.add(new ViewReviewsBean(r.getVideogame().getName(), r.getUser().getName(), r.getText(), r.getScore(), r.getDate()));
        }
        return lb;
    }
    public List<ViewReviewsBean> viewReviewsByGame(String videogame) throws SQLException, ClassNotFoundException, ItemNotFoundException{
        List<Review> list=DAOReview.getDAOReviewInstance().getReviewsByGame(videogame);
        if(list.isEmpty()){
            throw new ItemNotFoundException();
        }
        List<ViewReviewsBean> lb = new ArrayList<>();
        for(Review r : list){
            lb.add(new ViewReviewsBean(r.getVideogame().getName(), r.getUser().getName(), r.getText(), r.getScore(), r.getDate()));
        }
        return lb;
    }
    public List<ViewReviewsBean> viewReviewsByUser(String user) throws SQLException, ClassNotFoundException, ItemNotFoundException{
        User u=DAOUser.getDAOUserInstance().getUserByName(user);
        List<Review> list=DAOReview.getDAOReviewInstance().getReviewsByUser(u);
        if(list.isEmpty()){
            throw new ItemNotFoundException();
        }
        List<ViewReviewsBean> lb = new ArrayList<>();
        for(Review r : list){
            lb.add(new ViewReviewsBean(r.getVideogame().getName(), r.getUser().getName(), r.getText(), r.getScore(), r.getDate()));
        }
        return lb;
    }

}
