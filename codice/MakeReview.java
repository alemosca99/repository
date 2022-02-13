package com.example.demo;

import java.sql.SQLException;
import java.util.List;
import java.util.Date;

//classe <<controller>>

public class MakeReview {

    //metodi
    public void makeReview(MakeReviewBean b) throws SQLException, ClassNotFoundException, DuplicatedInstanceException {
        //controllo presenza duplicato
        List<Review> list=DAOReview.getDAOReviewInstance().getReviewsByGame(b.getUser());
        for(Review r : list){
            if(r.getVideogame().getName().equals(b.getVideogame())){
                throw new DuplicatedInstanceException();
            }
        }
        Videogame v=DAOVideogame.getDAOVideogameInstance().getVideogameByName(b.getVideogame());
        User u=DAOUser.getDAOUserInstance().getUserByName(b.getUser());
        Review review = new Review(v, u, b.getText(), b.getScore(), new Date());
        DAOReview.getDAOReviewInstance().insertReview(review);
        //aggiorna voto
        int newScore=calculateScore(v);
        DAOVideogame.getDAOVideogameInstance().updateScore(v.getName(), newScore);
    }
    public int calculateScore(Videogame v) throws SQLException, ClassNotFoundException{
        List<Review> list=DAOReview.getDAOReviewInstance().getReviewsByGame(v.getName());
        return averageScore(v, list);
    }
    public int averageScore(Videogame v, List<Review> list){
        if(list.isEmpty()){
            return -1;
        }
        for(Review r : list){
            if(!r.getVideogame().getName().equals(v.getName())){
                return -1;
            }
        }
        int newScore=0;
        for(Review r : list){
            newScore+=r.getScore();
        }
        newScore/=list.size();
        return newScore;
    }

}
