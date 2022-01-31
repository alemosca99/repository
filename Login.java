package com.example.demo;

import javafx.scene.control.Alert;

import java.util.List;

public class Login {

    //methods
    public Login(){};
    public LoginResultBean makeLogin(LoginBean b) throws Exception{
        List<User> list=DAOUser.getDAOUserInstance().getAllUsers();
        for(User u: list){
            if(u.getName().equals(b.getUser()) && u.getPassword().equals(b.getPassword())){
                LoginResultBean resultB = new LoginResultBean(u.getName(), u.isAdmin());
                return resultB;
            }
        }
        Exception e = new WrongInformationException();
        throw e;
    }

}
