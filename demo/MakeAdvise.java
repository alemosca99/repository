package com.example.demo;

import javafx.scene.control.Alert;

public class MakeAdvise {

    //methods
    public void MakeAdvise(MakeAdviseBean b) throws Exception{
        Alert a1 = new Alert(Alert.AlertType.INFORMATION, b.getText());
        a1.show();
        int newId=DAOAdvise.getDAOAdviseInstance().maxId(b.getUser());
        User u=DAOUser.getDAOUserInstance().getUserByName(b.getUser());
        Advise a = new Advise(newId, u, b.getText());
        DAOAdvise.getDAOAdviseInstance().insertAdvise(a);
    }
    public void makeAdviseResult(MakeAdviseResultBean b) throws  Exception{
        User mainUser=DAOUser.getDAOUserInstance().getUserByName(b.getAdviceUser());
        User otherUser=DAOUser.getDAOUserInstance().getUserByName(b.getUser());
        Videogame v=DAOVideogame.getDAOVideogameInstance().getVideogameByName(b.getVideogame());
        AdviseResult a = new AdviseResult(mainUser, b.getAdviceId(), otherUser, v);
        DAOAdviseResult.getDAOAdviseResultInstance().insertAdviseResult(a);
    }

}
