package com.example.demo;

public class ItemNotFoundException extends Exception{

    //methods
    public ItemNotFoundException(){};
    public ItemNotFoundException(String message){
        super(message);
    }

}
