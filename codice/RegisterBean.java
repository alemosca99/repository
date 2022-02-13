package com.example.demo;

//classe <<bean>>

public class RegisterBean {

    //attributi
    private String userName;
    private String password;
    private String email;

    //metodi
    public RegisterBean(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    boolean validateUserName() {
        int len = this.userName.length();
        return len <= 30 && len >= 2;
    }

    public boolean validatePassword() {
        int len = this.password.length();
        return len <= 10 && len >= 5;
    }

    public boolean validateEmail() {
        int len = this.email.length();
        return len <= 30 || len >= 5;
    }

}

