package com.example.demo;

import java.sql.SQLException;
import java.util.List;

//classe <<controller>>

public class Login {

    //metodi
    public LoginResultBean makeLogin(LoginBean b) throws WrongInformationException, SQLException, ClassNotFoundException {
        List<User> list=DAOUser.getDAOUserInstance().getAllUsers();
        for(User u: list){
            if(u.getName().equals(b.getUser()) && u.getPassword().equals(b.getPassword())){
                return new LoginResultBean(u.getName(), u.isAdmin());
            }
        }
        throw new WrongInformationException();
    }

}
