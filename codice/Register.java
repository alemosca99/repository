package com.example.demo;

import java.sql.SQLException;
import java.util.List;

//classe <<controller>>

public class Register {

    //metodi
    public String register(RegisterBean b) throws SQLException, ClassNotFoundException, DuplicatedInstanceException {
        //controllo duplicato
        List<User> list=DAOUser.getDAOUserInstance().getAllUsers();
        for(User u : list){
            if(u.getName().equals(b.getUserName())){
                throw new DuplicatedInstanceException();
            }
        }
        //inserimento
        User u = new User(b.getUserName(), b.getPassword(), b.getEmail(), false);
        DAOUser.getDAOUserInstance().insertUser(u);
        return u.getName();
    }

}
