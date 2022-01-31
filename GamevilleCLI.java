package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GamevilleCLI {

    //attributi
    private static Session s;

    public static void main(String arg[]) throws Exception {
        //variabili
        String m;
        boolean b = false;
        //preparazione sessione
        GamevilleCLI.s = new Session();
        //preparazione input
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //login
        boolean loginPassed;
        do {
            loginPassed = true;
            System.out.println("insert user name:");
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
        } while (!loginPassed);
        //menu principale
        do {
            System.out.println("choose:");
            System.out.println("----------------------");
            System.out.println("2:games");
            System.out.println("3:reviews");
            System.out.println("4:advice");
            System.out.println("DEFAULT:exit");
            System.out.println("----------------------");
            m = in.readLine();

            switch (m) {
                case "2":
                    viewGames();
                    break;
                case "3":
                    viewReviews("all", null);
                    break;
                case "4":
                    viewAdvice("all", null);
                    break;
                default:
                    b = true;
            }
        } while (!b);
        System.exit(0);
    }

    public static void viewGames() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        boolean b = false;
        boolean gameFound;
        ViewGames view = new ViewGames();
        List<ViewGamesBean> list;
        String game;
        try {
            list = view.viewGames();
            for (ViewGamesBean bean : list) {
                System.out.println(bean.getName() + " | " + bean.getScore() + " | " + bean.getGenre() + " | " + bean.getYear());
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
        } catch (ItemNotFoundException e) {
            System.out.println("there are not games");
        } catch (Exception e) {
            System.out.println("there was a problem");
        }
    }

    public static void viewReviews(String mode, String item) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
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
                System.out.println(bean.getUser() + " | " + bean.getVideogame() + " | " + bean.getText() + " | " + bean.getScore() + " | " + bean.getDate());
            }
            if (mode.equals("game")) {
                System.out.println("Do you want insert a review for this game?");
                if (in.readLine().equals("yes")) {
                    insertReview(item);
                }
            }
        } catch (ItemNotFoundException e) {
            System.out.println("no review found");
        } catch (Exception e) {
            System.out.println("there was a problem");
        }

    }
    public static void insertReview(String videogame) throws Exception{
        String text;
        int score;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        boolean b;
        MakeReviewBean bean;
        //preparazione recensione
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
        //inserimento recensione
        MakeReview make = new MakeReview();
        try{
            make.makeReview(bean);
            System.out.println("review correctly inserted");
        }
        catch(DuplicatedInstanceException e){
            System.out.println("there is another your review for this game");
        }
        catch(Exception e){
            System.out.println("there was a problem");
        }
    }
    public static void viewAdvice(String mode, String item){
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
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
                System.out.println(bean.getUser() + " | " + bean.getId() + " | " + bean.getText());
            }
        }
        catch(ItemNotFoundException e){
            System.out.println("there aren't advice");
        }
        catch(Exception e){
            System.out.println("there was a problem");
        }
    }

}
