package com.example.demo;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

//classe <<view>>

public class GamevilleCLI {

    //attributi
    private static Session s;
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static String barrier="-----------------------";
    private static String error="error";

    //metodi
    public static void main(String [] arg) throws IOException {
        //variabili
        String m;
        boolean b = false;
        //preparazione sessione
        GamevilleCLI.s = new Session();
        //login
        boolean loginPassed;
        do {
            loginPassed = true;
            System.out.println("are you new?");
            if(in.readLine().equals("yes")){
                registerUser();
            }
            System.out.println("insert name:");
            String userName = in.readLine();
            System.out.println("insert password:");
            String password = in.readLine();
            try {
                LoginBean bean = new LoginBean(userName, password);
                Login login = new Login();
                LoginResultBean bb = login.makeLogin(bean);
                GamevilleCLI.s.setUser(bb.getUser());
                GamevilleCLI.s.setAdmin(bb.isAdmin());
            } catch (WrongInformationException e) {
                loginPassed = false;
                System.out.println("wrong information");
            }
            catch (SQLException | ClassNotFoundException e) {
                System.out.println(error);
            }
        } while (!loginPassed);
        //menu principale
        do {
            System.out.println("choose:");
            System.out.println(barrier);
            System.out.println("1:user page");
            System.out.println("2:games");
            System.out.println("3:reviews");
            System.out.println("4:advice");
            if(s.isAdmin()){
                System.out.println("5:insert game");
            }
            System.out.println("DEFAULT: exit");
            System.out.println(barrier);
            m = in.readLine();
            switch (m) {
                case "1":
                    userPage();
                    break;
                case "2":
                    viewGames();
                    break;
                case "3":
                    viewReviews("all", null);
                    break;
                case "4":
                    viewAdvice("all", null);
                    break;
                case "5":
                    if(s.isAdmin()){
                        insertGame();
                    }
                    else{
                        b=true;
                    }
                    break;
                default:
                    b = true;
            }
        } while (!b);
        System.exit(0);
    }
    public static void registerUser() throws IOException{
        String name;
        String password;
        String email;
        System.out.println("insert name");
        name=in.readLine();
        System.out.println("insert password");
        password=in.readLine();
        System.out.println("insert email");
        email=in.readLine();
        RegisterBean bean = new RegisterBean(name, password, email);
        if(bean.validateUserName() && bean.validatePassword() && bean.validateEmail()){
            Register reg = new Register();
            try{
                reg.register(bean);
                System.out.println("user correctly registered");
            }
            catch(DuplicatedInstanceException e){
                System.out.println("the username is used");
            }
            catch (SQLException | ClassNotFoundException e) {
                System.out.println(error);
            }
        }
    }
    public static void insertGame() throws IOException{
        String name;
        String genre;
        int year;
        System.out.println("insert name");
        name=in.readLine();
        System.out.println("insert genre");
        genre=in.readLine();
        System.out.println("insert year");
        year=Integer.parseInt(in.readLine());
        InsertGameBean bean = new InsertGameBean(name, genre, year);
        if(bean.validateName() && bean.validateGenre() && bean.validateYear()){
            try{
                InsertGame insert = new InsertGame();
                insert.insertGame(bean);
                System.out.println("game correctly inserted");
            }
            catch(DuplicatedInstanceException e ){
                System.out.println("this game is present");
            }
            catch (SQLException | ClassNotFoundException e) {
                System.out.println(error);
            }
        }
    }
    public static void viewGames() throws IOException{
        boolean b = false;
        boolean gameFound;
        ViewGames view = new ViewGames();
        List<ViewGamesBean> list;
        String game;
        try {
            list = view.viewGames();
            for (ViewGamesBean bean : list) {
                System.out.println(barrier);
                System.out.println("NAME: " + bean.getName());
                System.out.println("SCORE: " + bean.getScore());
                System.out.println("GENRE: " + bean.getGenre());
                System.out.println("YEAR: " + bean.getYear());
            }
            do {
                System.out.println("Do you want view the reviews of a game?");
                if (in.readLine().equals("yes")) {
                    gameFound = false;
                    System.out.println("Insert the name of the game");
                    game = in.readLine();
                    for (ViewGamesBean bean : list) {
                        if (game.equals(bean.getName())) {
                            gameFound = true;
                            viewReviews("game", game);
                        }
                    }
                    if (!gameFound) {
                        System.out.println("Game not found");
                    }
                }
                System.out.println("Do you want exit?");
                if (in.readLine().equals("yes")) {
                    b = true;
                }
            } while (!b);
        }
        catch (ItemNotFoundException e) {
            System.out.println("there are not games");
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println(error);
        }
    }

    public static void viewReviews(String mode, String item) throws IOException{
        ViewReviews view = new ViewReviews();
        List<ViewReviewsBean> list;
        try {
            switch (mode) {
                case "all":
                    list = view.viewAllReviews();
                    break;
                case "game":
                    list = view.viewReviewsByGame(item);
                    break;
                case "user":
                    list = view.viewReviewsByUser(item);
                    break;
                default:
                    return;
            }
            for (ViewReviewsBean bean : list) {
                System.out.println(barrier);
                System.out.println("USER: " + bean.getUser());
                System.out.println("GAME: " + bean.getVideogame());
                System.out.println("DATE: " + bean.getDate());
                System.out.println("REVIEW: " + bean.getText());
                System.out.println("SCORE: " + bean.getScore());
            }
        }
        catch(ItemNotFoundException e){
            System.out.println("there aren't reviews");
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println(error);
        }
        if (mode.equals("game")) {
            System.out.println("Do you want insert a review for this game?");
            if (in.readLine().equals("yes")) {
                insertReview(item);
            }
        }
    }
    public static void insertReview(String videogame) throws IOException{
        String text;
        int score;
        boolean b;
        MakeReviewBean bean;
        //preparazione recensione

        //inserimento recensione
        MakeReview make = new MakeReview();
        try{
            do {
                b = false;
                System.out.println("insert the text");
                text = in.readLine();
                System.out.println("insert the score");
                score = Integer.parseInt(in.readLine());
                bean = new MakeReviewBean(videogame, s.getUser(), text, score);
                if (bean.validateText() && bean.validateScore()) {
                    b = true;
                } else {
                    System.out.println("wrong input");
                }
            }while(!b);
            make.makeReview(bean);
            System.out.println("review correctly inserted");
        }
        catch(DuplicatedInstanceException e){
            System.out.println("there is another your review for this game");
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println(error);
        }
    }
    public static void viewAdvice(String mode, String item) throws IOException{
        ViewAdvice view = new ViewAdvice();
        List<ViewAdviceBean> list;
        try{
            switch(mode){
                case "all":
                    list=view.viewAllAdvice();
                    break;
                case "user":
                    list=view.viewAdviceByUser(item);
                    break;
                default:
                    return;
            }
            for (ViewAdviceBean bean : list) {
                System.out.println(barrier);
                System.out.println("USER: " + bean.getUser());
                System.out.println("ID: " + bean.getId());
                System.out.println("ADVISE: " + bean.getText());
            }
            System.out.println("Do you want view a result?");
            if(in.readLine().equals("yes")){
                viewResult();
            }
            System.out.println("Do you want insert a result?");
            if(in.readLine().equals("yes")){
                insertResult();
            }
            System.out.println("Do you want insert an advise?");
            if(in.readLine().equals("yes")){
                insertAdvise();
            }
        }
        catch(ItemNotFoundException e){
            System.out.println("there aren't advice");
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println(error);
        }
    }

    public static void viewResult() throws IOException{
        ViewAdvice view = new ViewAdvice();
        try{
            String user;
            int id;
            System.out.println("insert the user of the advise");
            user=in.readLine();
            System.out.println("insert the id of the advise");
            id=Integer.parseInt(in.readLine());
            List<ViewAdviseResultBean> list;
            ViewAdviseResultInputBean bean = new ViewAdviseResultInputBean(user, id);
            list=view.viewAdviceResults(bean);
            for(ViewAdviseResultBean b : list){
                System.out.println(barrier);
                System.out.println("GAME: " + b.getVideogame());
                System.out.println("NUMBER OF ADVICE: " + b.getAdvice());
            }
        }
        catch(ItemNotFoundException e){
            System.out.println("there are not results for this advise");
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println(error);
        }
    }
    public static void insertAdvise() throws IOException{
        boolean b=false;
        MakeAdvise make = new MakeAdvise();
        MakeAdviseBean bean;
        try{
            String advise;
            do{
                System.out.println("insert the advise:");
                advise=in.readLine();
                bean = new MakeAdviseBean(s.getUser(), advise);
                if(bean.validateText()){
                    b=true;
                }
                else{
                    System.out.println("wrong information");
                }
            }while(!b);
            make.makeAdvise(bean);
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println(error);
        }
    }
    public static void insertResult() throws IOException{
        MakeAdvise make = new MakeAdvise();
        MakeAdviseResultBean bean;
        try{
            String user;
            int id;
            String game;
            System.out.println("insert the user of the advise:");
            user=in.readLine();
            System.out.println("insert the id of the advise:");
            id=Integer.parseInt(in.readLine());
            System.out.println("insert the game:");
            game=in.readLine();
            bean = new MakeAdviseResultBean(s.getUser(), id, user, game);
            make.makeAdviseResult(bean);
            System.out.println("result correctly inserted");
        }
        catch(DuplicatedInstanceException e){
            System.out.println("another your result for this advise is present");
        }
        catch (SQLException | ClassNotFoundException e) {
            System.out.println(error);
        }
    }
    private static void userPage() throws IOException{
        String m;
        boolean b = false;
        do {
            System.out.println(barrier);
            System.out.println("1:your reviews");
            System.out.println("2:your advice");
            System.out.println("DEFAULT: exit");
            System.out.println(barrier);
            m = in.readLine();
            switch (m) {
                case "1":
                    viewReviews("user", s.getUser());
                    break;
                case "2":
                    viewAdvice("user", s.getUser());
                    break;
                default:
                    b = true;
            }
        } while (!b);
    }

}
