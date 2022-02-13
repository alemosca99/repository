package com.example.demo;

//classe <<controller>>

import java.sql.SQLException;

public class MakeAdvise {

    //metodi
    public void makeAdvise(MakeAdviseBean b) throws SQLException, ClassNotFoundException{
        int newId=DAOAdvise.getDAOAdviseInstance().maxId(b.getUser());
        User u=DAOUser.getDAOUserInstance().getUserByName(b.getUser());
        Advise a = new Advise(newId, u, b.getText());
        DAOAdvise.getDAOAdviseInstance().insertAdvise(a);
    }
    public void makeAdviseResult(MakeAdviseResultBean b) throws  SQLException, ClassNotFoundException, DuplicatedInstanceException{
        User mainUser=DAOUser.getDAOUserInstance().getUserByName(b.getAdviceUser());
        User otherUser=DAOUser.getDAOUserInstance().getUserByName(b.getUser());
        Videogame v=DAOVideogame.getDAOVideogameInstance().getVideogameByName(b.getVideogame());
        Advise a=DAOAdvise.getDAOAdviseInstance().getAdvise(b.getAdviceUser(), b.getAdviceId());
        //controllo duplicato
        for(AdviseResult ar : a.getResult()){
            if(ar.getOtherUser().getName().equals(b.getUser())){
                throw new DuplicatedInstanceException();
            }
        }
        //inserimento risultato
        DAOAdviseResult.getDAOAdviseResultInstance().insertAdviseResult(new AdviseResult(mainUser, b.getAdviceId(), otherUser, v));
    }

}
