package com.example.demo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

//classe <<view>>

public class Gameville extends Application{

    //attributi
    private Scene login;
    private Scene register;
    private Scene mainMenu;
    private Scene userPage;
    private Scene userReviews;
    private Scene userAdvice;
    private Scene games;
    private Scene gameReviews;
    private Scene makeReview;
    private Scene reviews;
    private Scene advice;
    private Scene makeAdvise;
    private Scene adviseResult;
    private Scene makeAdviseResult;
    private Scene insertGame;
    private Session s;
    private static final double SCREENWIDTH=Screen.getPrimary().getBounds().getWidth();
    private static final String ERROR="error";
    private static final String PROBLEM="there was a problem";
    private static final String DELFINO="Delfino";
    private static final String INSERT="INSERT";
    private static final String VIDEOGAME="videogame";
    //metodi preparazione scene
    private void prepareLogin(final Stage stage) throws IOException{
        VBox root=(VBox)login.getRoot();
        root.getChildren().clear();
        VBox loginForm=prepareVBox(1000, 0, 20, 5, Color.LIGHTGREY, Pos.CENTER, 20);
        final TextField nameLogin=prepareTextField(300, 0);
        final PasswordField passwordLogin=preparePasswordField(300, 0);
        Button loginExecutionButton=prepareButton("LOG-IN", 0, 0);
        loginExecutionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent){
                LoginBean logB = new LoginBean(nameLogin.getText(), passwordLogin.getText());
                try{
                    Login log = new Login();
                    LoginResultBean b=log.makeLogin(logB);
                    s.setUser(b.getUser());
                    s.setAdmin(b.isAdmin());
                    prepareMainMenu(stage);
                    stage.setScene(mainMenu);
                }
                catch(WrongInformationException e){
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "the user is not existent");
                    a.show();
                }
                catch (SQLException | ClassNotFoundException e){
                    Alert a = new Alert(Alert.AlertType.INFORMATION, PROBLEM);
                    a.show();
                    System.exit(-1);
                }
            }
        });
        Button registerButton=prepareButton("REGISTER", 0, 0);
        this.setButtonGoAction(registerButton, stage, register);
        loginForm.getChildren().addAll(prepareText("NAME", 20), nameLogin, prepareText("PASSWORD", 20), passwordLogin, loginExecutionButton, registerButton);
        root.getChildren().addAll(prepareTitleBox(), loginForm);
    }
    private void prepareRegister(final Stage stage){
        VBox root=(VBox)register.getRoot();
        VBox registerForm=prepareVBox(1000, 0, 20, 5, Color.LIGHTGREY, Pos.CENTER, 20);
        final TextField nameRegister=prepareTextField(300, 0);
        final TextField passwordRegister=prepareTextField(300, 0);
        final TextField emailRegister=prepareTextField(300, 0);
        Button registerExecutionButton=prepareButton("REGISTER", 0, 0);
        registerExecutionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                RegisterBean b = new RegisterBean(nameRegister.getText(), passwordRegister.getText(), emailRegister.getText());
                Register reg = new Register();
                if(b.validateUserName() && b.validatePassword() && b.validateEmail()){
                    try{
                        reg.register(b);
                        stage.setScene(login);
                    }
                    catch (DuplicatedInstanceException e){
                        Alert a = new Alert(Alert.AlertType.INFORMATION, "the username is used");
                        a.show();
                    }
                    catch (SQLException | ClassNotFoundException e){
                        Alert a = new Alert(Alert.AlertType.INFORMATION, PROBLEM);
                        a.show();
                    }
                }
                else{
                    Alert a = new Alert(Alert.AlertType.INFORMATION, ERROR);
                    a.show();
                }
            }
        });
        registerForm.getChildren().addAll(prepareText("NAME", 20), nameRegister, prepareText("PASSWORD", 20),  passwordRegister, prepareText("EMAIL", 20), emailRegister, registerExecutionButton);
        root.getChildren().addAll(prepareTitleBox(), registerForm, prepareReturnButton(stage, login));
    }
    private void prepareMainMenu(final Stage stage){
        VBox root=(VBox) mainMenu.getRoot();
        root.getChildren().clear();
        Button userPageButton=prepareButton("USER", 400, 0);
        Button gamesButton=prepareButton("GAMES", 400, 0 );
        Button adviceButton=prepareButton("ADVICE", 400, 0);
        Button reviewsButton=prepareButton("REVIEWS", 400, 0);
        root.getChildren().addAll(prepareTitleBox(), userPageButton, gamesButton, adviceButton, reviewsButton);
        userPageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                prepareUserPage(stage);
                stage.setScene(userPage);
            }
        });
        gamesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                prepareGames(stage);
                stage.setScene(games);
            }
        });
        reviewsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                prepareReviews("all", null, stage);
                stage.setScene(reviews);
            }
        });
        adviceButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                prepareAdvice("all", null, stage);
                stage.setScene(advice);
            }
        });
        if(s.isAdmin()){
            Button insertGameButton=prepareButton("INSERT GAME", 400, 0);
            insertGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage.setScene(insertGame);
                }
            });
            root.getChildren().add(insertGameButton);
        }
        Button exitButton=prepareButton("EXIT", 400, 0);
        exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent){
                try{
                    prepareLogin(stage);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                stage.setScene(login);
            }
        });
        root.getChildren().add(exitButton);
    }
    private void prepareUserPage(final Stage stage){
        VBox root=(VBox)userPage.getRoot();
        root.getChildren().clear();
        VBox userForm=prepareVBox(1000, 0, 20, 5, Color.LIGHTGREY, Pos.CENTER, 20);
        Text userName=prepareText(s.getUser(), 20);
        Button userReviewsButton=prepareButton("YOUR REVIEWS", 500, 100);
        Button userAdviceButton=prepareButton("YOUR ADVICE", 500, 100);
        userForm.getChildren().addAll(userName, userReviewsButton, userAdviceButton);
        userReviewsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                prepareReviews("user", s.getUser(), stage);
                stage.setScene(userReviews);
            }
        });
        userAdviceButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                prepareAdvice("user", s.getUser(), stage);
                stage.setScene(userAdvice);
            }
        });
        root.getChildren().addAll(prepareTitleBox(), userForm, prepareReturnButton(stage, mainMenu));
    }
    private void prepareGames(final Stage stage) {
        VBox rootGames= (VBox) games.getRoot();
        rootGames.getChildren().clear();
        rootGames.getChildren().add(prepareTitleBox());
        ScrollPane scroll=prepareScrollPane(1500, 1000, 3, Color.LIGHTGREY);
        rootGames.getChildren().add(scroll);
        try {
            ViewGames view = new ViewGames();
            List<ViewGamesBean> list = view.viewGames();
            GridPane g=prepareGridPane(1500, 0, 0, Color.LIGHTGREY, Pos.CENTER, 30);
            g.setHgap(60);
            g.setVgap(30);
            scroll.setContent(g);
            for (int i=0; i < list.size(); i++) {
                final ViewGamesBean vb=list.get(i);
                Text gameName=prepareText(vb.getName(), 20);
                g.add(gameName, 0, i);
                VBox gameScore=prepareGameViewScore(vb.getScore());
                g.add(gameScore, 1, i);
                Text gameGenre=prepareText(vb.getGenre(), 20);
                g.add(gameGenre, 2, i);
                Text gameYear=prepareText(String.valueOf(vb.getYear()), 20);
                g.add(gameYear, 3, i);
                Button b=prepareButton("REVIEWS", 300, 50);
                b.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        prepareReviews(VIDEOGAME, vb.getName(), stage);
                        stage.setScene(gameReviews);
                    }
                });
                g.add(b, 4, i);
            }
            rootGames.getChildren().add(g);
        }
        catch(ItemNotFoundException e){
            Alert a = new Alert(Alert.AlertType.INFORMATION, "there aren't games");
            a.show();
        }
        catch (SQLException | ClassNotFoundException e){
            Alert a = new Alert(Alert.AlertType.INFORMATION, PROBLEM);
            a.show();
        }
        finally{
            Button returnGameButton=prepareReturnButton(stage, mainMenu);
            rootGames.getChildren().add(returnGameButton);
        }
    }
    private void prepareMakeReview(final String videogame, final Stage stage){
        VBox root=(VBox)makeReview.getRoot();
        root.getChildren().clear();
        VBox reviewForm=prepareVBox(1000, 0, 20, 5, Color.LIGHTGREY, Pos.CENTER, 20);
        final TextArea review=prepareTextArea(800, 400);
        final ChoiceBox<String> score=prepareScore();
        Button insertReviewButton=prepareButton(INSERT, 0, 0);
        reviewForm.getChildren().addAll(prepareText("REVIEW", 20), review, prepareText("SCORE", 20), score, insertReviewButton);
        insertReviewButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MakeReviewBean b = new MakeReviewBean(videogame, s.getUser(), review.getText(), Integer.parseInt(score.getValue()));
                MakeReview make = new MakeReview();
                if(b.validateText()){
                    try{
                        make.makeReview(b);
                        Alert a = new Alert(Alert.AlertType.INFORMATION, "review correctly inserted");
                        a.show();
                        prepareReviews("game", videogame, stage);
                        stage.setScene(gameReviews);
                    }
                    catch(DuplicatedInstanceException e){
                        Alert a = new Alert(Alert.AlertType.INFORMATION, "another your review present");
                        a.show();
                    }
                    catch (SQLException | ClassNotFoundException e){
                        Alert a = new Alert(Alert.AlertType.INFORMATION, PROBLEM);
                        a.show();
                    }
                }
                else{
                    Alert a = new Alert(Alert.AlertType.INFORMATION, ERROR);
                    a.show();
                }
            }
        });
        Button returnButtonMakeReview=prepareReturnButton(stage, mainMenu);
        root.getChildren().addAll(prepareTitleBox(), reviewForm, returnButtonMakeReview);
        this.setButtonGoAction(returnButtonMakeReview, stage, mainMenu);
    }
    private void prepareReviews(String mode, String item, final Stage stage){
        VBox root;
        switch(mode){
            case "user":
                root=(VBox) userReviews.getRoot();
                break;
            case VIDEOGAME:
                root=(VBox) gameReviews.getRoot();
                break;
            case  "all":
                root=(VBox) reviews.getRoot();
                break;
            default:
                return;
        }
        root.getChildren().clear();
        root.getChildren().add(prepareTitleBox());
        try{
            ViewReviews view = new ViewReviews();
            List<ViewReviewsBean> list;
            switch(mode){
                case "user":
                    list=view.viewReviewsByUser(item);
                    break;
                case VIDEOGAME:
                    list=view.viewReviewsByGame(item);
                    break;
                case  "all":
                    list=view.viewAllReviews();
                    break;
                default:
                    return;
            }
            ScrollPane scroll=prepareScrollPane(1500, 1000, 3, Color.LIGHTGREY);
            VBox vList = prepareVBox(1500, 0, 20, 0, Color.LIGHTGREY, Pos.CENTER, 20);
            scroll.setContent(vList);
            for(ViewReviewsBean rb : list){
                GridPane g=prepareGridPane(1400, 400, 3, Color.LIGHTGREY, Pos.CENTER, 0);
                g.setVgap(10);
                g.setHgap(10);
                RowConstraints row = new RowConstraints();
                row.setFillHeight(false);
                row.setValignment(VPos.CENTER);
                ColumnConstraints col1 = new ColumnConstraints();
                col1.setFillWidth(false);
                col1.setHalignment(HPos.CENTER);
                col1.setPercentWidth(25);
                ColumnConstraints col2 = new ColumnConstraints();
                col2.setFillWidth(false);
                col2.setHalignment(HPos.CENTER);
                col2.setPercentWidth(50);
                Text reviewUser=prepareText(rb.getUser(), 20);
                Text reviewVideogame=prepareText(rb.getVideogame(), 30);
                Text reviewDate=prepareText(rb.getDate().toString(), 20);
                TextArea reviewText=prepareTextArea(400, 200);
                reviewText.setEditable(false);
                reviewText.setText(rb.getText());
                VBox reviewScore=prepareGameViewScore(rb.getScore());
                g.add(reviewUser, 0, 0);
                g.add(reviewVideogame, 1, 0);
                g.add(reviewDate, 2, 0);
                g.add(reviewText, 1, 1);
                g.add(reviewScore, 1, 2);
                g.getRowConstraints().addAll(row, row, row);
                g.getColumnConstraints().addAll(col1, col2, col1);
                vList.getChildren().add(g);
            }
            root.getChildren().add(scroll);
        }
        catch(ItemNotFoundException e){
            String message;
            switch(mode){
                case "user":
                    message="you have not reviews";
                    break;
                case VIDEOGAME:
                    message="there aren't reviews for this game";
                    break;
                case "all":
                    message="there aren't reviews";
                    break;
                default:
                    return;
            }
            Alert a = new Alert(Alert.AlertType.INFORMATION, message);
            a.show();
        }
        catch (SQLException | ClassNotFoundException e){
            Alert a = new Alert(Alert.AlertType.INFORMATION, PROBLEM);
            a.show();
        }
        finally{
            if(mode.equals(VIDEOGAME)){
                Button makeReviewButton=prepareButton("INSERT REVIEW", 300, 50);
                prepareMakeReview(item, stage);
                setButtonGoAction(makeReviewButton, stage, makeReview);
                root.getChildren().add(makeReviewButton);
            }
            Button returnButton;
            switch(mode){
                case "user":
                    returnButton=prepareReturnButton(stage, userPage);
                    root.getChildren().add(returnButton);
                    break;
                case VIDEOGAME:
                    returnButton=prepareReturnButton(stage, games);
                    root.getChildren().add(returnButton);
                    break;
                case  "all":
                    returnButton=prepareReturnButton(stage, mainMenu);
                    root.getChildren().add(returnButton);
                    break;
                default:
            }
        }
    }
    private void prepareAdvice(final String mode, String item, final Stage stage){
        VBox root;
        switch(mode){
            case "user":
                root=(VBox) userAdvice.getRoot();
                break;
            case  "all":
                root=(VBox) advice.getRoot();
                break;
            default:
                return;
        }
        root.getChildren().clear();
        root.getChildren().add(prepareTitleBox());
        try{
            ViewAdvice view = new ViewAdvice();
            List<ViewAdviceBean> list;
            switch(mode){
                case "user":
                    list=view.viewAdviceByUser(item);
                    break;
                case  "all":
                    list=view.viewAllAdvice();
                    break;
                default:
                    return;
            }
            if(!list.isEmpty()){
                ScrollPane scroll=prepareScrollPane(1500, 1000, 3, Color.LIGHTGREY);
                VBox vList = prepareVBox(1500, 0, 20, 0, Color.LIGHTGREY, Pos.CENTER, 20);
                scroll.setContent(vList);
                for(final ViewAdviceBean rb : list){
                    GridPane g=prepareGridPane(1200, 300, 3, Color.LIGHTGREY, Pos.CENTER, 20);
                    ColumnConstraints col1 = new ColumnConstraints();
                    col1.setFillWidth(false);
                    col1.setHalignment(HPos.CENTER);
                    col1.setPercentWidth(25);
                    ColumnConstraints col2 = new ColumnConstraints();
                    col2.setFillWidth(false);
                    col2.setHalignment(HPos.CENTER);
                    col2.setPercentWidth(50);
                    g.getColumnConstraints().addAll(col1, col2, col1);
                    final Text adviseId=prepareText(String.valueOf(rb.getId()), 20);
                    final Text adviseUser=prepareText(rb.getUser(), 20);
                    TextArea adviseText=prepareTextArea(400, 200);
                    adviseText.setEditable(false);
                    adviseText.setText(rb.getText());
                    Button viewResult=prepareButton("VIEW RESULT", 0, 0);
                    viewResult.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            prepareResult(mode, adviseUser.getText(), Integer.parseInt(adviseId.getText()), stage);
                            stage.setScene(adviseResult);
                        }
                    });
                    Button insertResult=prepareButton("INSERT RESULT", 0, 0);
                    insertResult.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            prepareMakeResult(rb.getId(), rb.getUser(), stage);
                            stage.setScene(makeAdviseResult);
                        }
                    });
                    g.add(adviseId, 0, 0);
                    g.add(adviseUser, 2, 0);
                    g.add(adviseText, 1, 1);
                    g.add(viewResult, 0, 2);
                    if(mode.equals("all")){
                        g.add(insertResult, 2, 2);
                    }
                    vList.getChildren().add(g);
                }
                root.getChildren().add(scroll);
            }
        }
        catch(ItemNotFoundException e){
            String message;
            switch(mode){
                case "user":
                    message="you have not advice";
                    break;
                case "all":
                    message="there aren't advice";
                    break;
                default:
                    return;
            }
            Alert a = new Alert(Alert.AlertType.INFORMATION, message);
            a.show();
        }
        catch (SQLException | ClassNotFoundException e){
            Alert a = new Alert(Alert.AlertType.INFORMATION, PROBLEM);
            a.show();
        }
        finally{
            if(mode.equals("all")){
                Button makeAdviseExecutionButton=prepareButton("INSERT ADVISE", 300, 50);
                makeAdviseExecutionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        stage.setScene(makeAdvise);
                    }
                });
                root.getChildren().add(makeAdviseExecutionButton);
            }
            Button returnButton;
            switch(mode){
                case "user":
                    returnButton=prepareReturnButton(stage, userPage);
                    root.getChildren().add(returnButton);
                    break;
                case  "all":
                    returnButton=prepareReturnButton(stage, mainMenu);
                    root.getChildren().add(returnButton);
                    break;
                default:
            }
        }
    }
    private void prepareMakeAdvise(final Stage stage){
        VBox root=(VBox)makeAdvise.getRoot();
        VBox adviseForm=prepareVBox(1000, 0, 20, 5, Color.LIGHTGREY, Pos.CENTER, 20);
        final TextArea advise=prepareTextArea(800, 400);
        Button insertAdviseButton=prepareButton("INSERT ADVICE", 300, 50);
        insertAdviseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MakeAdviseBean b = new MakeAdviseBean(s.getUser(), advise.getText());
                if(b.validateText()){
                    try{
                        MakeAdvise make = new MakeAdvise();
                        make.makeAdvise(b);
                        prepareAdvice("all", null, stage);
                        stage.setScene(advice);
                        Alert a = new Alert(Alert.AlertType.INFORMATION, "advise correctly inserted");
                        a.show();
                    }
                    catch(SQLException | ClassNotFoundException e){
                        Alert a = new Alert(Alert.AlertType.INFORMATION, PROBLEM);
                        a.show();
                    }

                }
                else{
                    Alert a = new Alert(Alert.AlertType.INFORMATION, ERROR);
                    a.show();
                }

            }
        });
        adviseForm.getChildren().addAll(advise, insertAdviseButton);
        root.getChildren().addAll(prepareTitleBox(), adviseForm, prepareReturnButton(stage, mainMenu));
    }
    private void prepareResult(String mode, String user, int id, final Stage stage){
        VBox root=(VBox)adviseResult.getRoot();
        root.getChildren().clear();
        root.getChildren().add(prepareTitleBox());
        try{
            ViewAdvice view = new ViewAdvice();
            ViewAdviseResultInputBean bean = new ViewAdviseResultInputBean(user, id);
            List<ViewAdviseResultBean> list=view.viewAdviceResults(bean);
            ScrollPane scroll=prepareScrollPane(1500, 1000, 3, Color.LIGHTGREY);
            GridPane g=prepareGridPane(1500, 0, 0, Color.LIGHTGREY, Pos.CENTER, 0);
            g.setHgap(80);
            scroll.setContent(g);
            for(int i=0; i < list.size(); i++){
                ViewAdviseResultBean b=list.get(i);
                Text adviseResultGame=prepareText(b.getVideogame(), 20);
                Text adviseResultNumber=prepareText(String.valueOf(b.getAdvice()), 20);
                g.add(adviseResultGame, 0, i );
                g.add(adviseResultNumber, 1, i);
            }
            root.getChildren().add(scroll);
        }
        catch (ItemNotFoundException e){
            Alert a = new Alert(Alert.AlertType.INFORMATION, "there are no result for thia advise");
            a.show();
        }
        catch (SQLException | ClassNotFoundException e){
            Alert a = new Alert(Alert.AlertType.INFORMATION, PROBLEM);
            a.show();
        }
        finally{
            Scene scene;
            switch (mode) {
                case "user":
                    scene = userAdvice;
                    root.getChildren().add(prepareReturnButton(stage, scene));
                    break;
                case "all":
                    scene = advice;
                    root.getChildren().add(prepareReturnButton(stage, scene));
                    break;
                default:
            }
        }
    }
    private void prepareMakeResult(int id, String user, final Stage stage){
        VBox root=(VBox)makeAdviseResult.getRoot();
        root.getChildren().clear();
        VBox insertResultForm=prepareVBox(1000, 0, 20, 5, Color.LIGHTGREY, Pos.CENTER, 20);
        HBox h=prepareHBox(1000, 0, 20, 0, Color.LIGHTGREY, Pos.CENTER, 20);
        final Text adviseId=prepareText(String.valueOf(id), 20);
        final Text adviseUser=prepareText(user, 20);
        h.getChildren().addAll(adviseId, adviseUser);
        final TextArea vid=prepareTextArea(500, 20);
        Button makeResultExecutionButton=prepareButton(INSERT, 0, 0);
        makeResultExecutionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MakeAdvise make = new MakeAdvise();
                MakeAdviseResultBean b = new MakeAdviseResultBean(s.getUser(), Integer.parseInt(adviseId.getText()), adviseUser.getText(), vid.getText());
                try{
                    make.makeAdviseResult(b);
                    prepareAdvice("all", null, stage);
                    stage.setScene(advice);
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "advise result correctly inserted");
                    a.show();
                    prepareAdvice("all", null, stage);
                    stage.setScene(advice);
                }
                catch(DuplicatedInstanceException e){
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "your result for this advise is present");
                    a.show();
                }
                catch(SQLException | ClassNotFoundException e){
                    Alert a = new Alert(Alert.AlertType.INFORMATION, PROBLEM);
                    a.show();
                }
            }
        });
        insertResultForm.getChildren().addAll(h, vid, makeResultExecutionButton);
        root.getChildren().addAll(prepareTitleBox(), insertResultForm, prepareReturnButton(stage, mainMenu));
    }
    private void prepareInsertGame(final Stage stage){
        VBox root=(VBox)insertGame.getRoot();
        VBox insertGameForm=prepareVBox(1000, 0, 29, 5, Color.LIGHTGREY, Pos.CENTER, 20);
        final Text videogameName=prepareText("NAME", 20);
        final TextField videogameNameField=prepareTextField(500, 0);
        final Text videogameGenre=prepareText("GENRE", 20);
        final ChoiceBox<String> videogameGenreField=prepareGenre();
        final Text videogameYear=prepareText("YEAR", 20);
        final TextField videogameYearField=prepareTextField(500, 0);
        Button insertGameExecutionButton=prepareButton(INSERT, 500, 20);
        insertGameExecutionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                InsertGameBean b = new InsertGameBean(videogameNameField.getText(), videogameGenreField.getValue(), Integer.parseInt(videogameYearField.getText()));
                if(b.validateName() && b.validateYear()){
                    try{
                        InsertGame ins = new InsertGame();
                        ins.insertGame(b);
                        stage.setScene(mainMenu);
                    }
                    catch(DuplicatedInstanceException e){
                        Alert a = new Alert(Alert.AlertType.INFORMATION, "there is another videogame with the same name");
                        a.show();
                    }
                    catch(SQLException | ClassNotFoundException e){
                        Alert a = new Alert(Alert.AlertType.INFORMATION, PROBLEM);
                        a.show();
                    }
                }
                else{
                    Alert a = new Alert(Alert.AlertType.INFORMATION, ERROR);
                    a.show();
                }
            }
        });
        insertGameForm.getChildren().addAll(videogameName, videogameNameField, videogameGenre, videogameGenreField, videogameYear, videogameYearField, insertGameExecutionButton);
        root.getChildren().addAll(prepareTitleBox(), insertGameForm, prepareReturnButton(stage, mainMenu));
    }
    //metodi creazione elementi grafici
    private void prepareStage(final Stage s){
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        s.setX(bounds.getMinX());
        s.setY(bounds.getMinY());
        s.setWidth(bounds.getWidth());
        s.setHeight(bounds.getHeight());
        s.setTitle("GAMEVILLE");
    }
    private VBox prepareVBox(double width, double height, double spacing, double borderWidths, Color backgroundColor, Pos position, double padding){
        VBox v= new VBox(spacing);
        prepareBox(v, width, height, borderWidths, backgroundColor, padding);
        v.setAlignment(position);
        return v;
    }
    private HBox prepareHBox(double width, double height, double spacing, double borderWidths, Color backgroundColor, Pos position, double padding){
        HBox h= new HBox(spacing);
        prepareBox(h, width, height, borderWidths, backgroundColor, padding);
        h.setAlignment(position);
        return h;
    }
    private GridPane prepareGridPane(double width, double height, double borderWidths, Color backgroundColor, Pos position, double padding){
        GridPane g = new GridPane();
        prepareBox(g, width, height, borderWidths, backgroundColor, padding);
        g.setAlignment(position);
        return g;
    }
    private void prepareBox(Region r, double width, double height, double borderWidths, Color backgroundColor, double padding){
        if(width > 0){
            r.setPrefWidth(width);
        }
        if(height > 0){
            r.setPrefHeight(height);
        }
        Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(borderWidths)));
        Background background = new Background(new BackgroundFill(backgroundColor, null, null));
        r.setBorder(border);
        r.setBackground(background);
        if(padding > 0){
            r.setPadding(new Insets(padding));
        }
    }
    private ScrollPane prepareScrollPane(double width, double height, int borderWidths, Color backgroundColor){
        ScrollPane sc = new ScrollPane();
        sc.setPrefWidth(width);
        sc.setMaxHeight(height);
        Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(borderWidths)));
        Background background = new Background(new BackgroundFill(backgroundColor, null, null));
        sc.setBorder(border);
        sc.setBackground(background);
        sc.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return sc;
    }
    private Text prepareText(String text, int size){
        Text t = new Text(text);
        t.setFont(new Font(DELFINO, size));
        return t;
    }
    private TextField prepareTextField(double width, double heigth){
        TextField t = new TextField();
        Border lightBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null));
        t.setFont(new Font(DELFINO, 20));
        if(width > 0){
            t.setMaxWidth(width);
        }
        if(heigth > 0){
            t.setPrefHeight(heigth);
        }
        t.setBorder(lightBorder);
        return t;
    }
    private PasswordField preparePasswordField(double width, double heigth){
        PasswordField t = new PasswordField();
        Border lightBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null));
        t.setFont(new Font(DELFINO, 20));
        if(width > 0){
            t.setMaxWidth(width);
        }
        if(heigth > 0){
            t.setPrefHeight(heigth);
        }
        t.setBorder(lightBorder);
        return t;
    }
    private TextArea prepareTextArea(double width, double height){
        TextArea t = new TextArea();
        Border lightBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null));
        t.setFont(new Font(DELFINO, 20));
        t.setBorder(lightBorder);
        t.setMaxWidth(width);
        t.setMaxHeight(height);
        t.setWrapText(true);
        return t;
    }
    private Button prepareButton(String text, double width, double height){
        Button b = new Button();
        Border lightBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(4)));
        Background buttonBackground = new Background(new BackgroundFill(Color.LIGHTGREY, null, null));
        b.setFont(new Font(DELFINO, 30));
        b.setText(text);
        b.setBorder(lightBorder);
        b.setBackground(buttonBackground);
        if(width != 0){
            b.setPrefWidth(width);
        }
        if(height != 0){
            b.setPrefHeight(height);
        }
        this.setButtonAnimations(b);
        return b;
    }
    private Button prepareReturnButton(final Stage stage, final Scene scene){
        Button b=prepareButton("RETURN", 0, 0);
        b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setScene(scene);
            }
        });
        return b;
    }
    private VBox prepareRoot(Background background){
        VBox root = new VBox(30);
        root.setBackground(background);
        root.setAlignment(Pos.TOP_CENTER);
        root.setFillWidth(false);
        return root;
    }
    private HBox prepareTitleBox(){
        HBox titleBox = new HBox(30);
        titleBox.setPrefWidth(SCREENWIDTH);
        titleBox.setPrefHeight(200);
        Background titleBackground = new Background(new BackgroundFill(Color.LIGHTGREY, null, null));
        Border normalBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(5)));
        titleBox.setBackground(titleBackground);
        titleBox.setBorder(normalBorder);
        titleBox.setAlignment(Pos.CENTER);
        Text t = new Text("Gameville");
        t.setFont(new Font(DELFINO, 100));
        t.setStroke(Color.RED);
        t.setStrokeWidth(2);
        titleBox.getChildren().add(t);
        return titleBox;
    }
    private VBox prepareGameViewScore(int score){
        VBox v;
        Color c=Color.GREEN;
        if(score == -1){
            c=Color.GRAY;
        }
        if(score < 6 && score >= 0){
            c=Color.RED;
        }
        if(score == 6 || score == 7){
            c=Color.YELLOW;
        }
        v=prepareVBox(50, 50, 10, 2, c, Pos.CENTER, 5);
        if(score != -1) {
            Text t=prepareText(String.valueOf(score), 20);
            v.getChildren().add(t);
        }
        return v;
    }
    private ChoiceBox<String> prepareScore(){
        ChoiceBox<String> score = new ChoiceBox<>();
        Border lightBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null));
        score.getItems().addAll("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        score.setBorder(lightBorder);
        score.setMaxWidth(100);
        score.setMaxHeight(50);
        return score;
    }
    private ChoiceBox<String> prepareGenre(){
        ChoiceBox<String> score = new ChoiceBox<>();
        Border lightBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null));
        score.getItems().addAll("platform", "action", "shooter", "fighter game", "survival horror", "life simulator");
        score.setBorder(lightBorder);
        score.setMaxWidth(100);
        score.setMaxHeight(50);
        return score;
    }
    //altri metodi
    private void setButtonAnimations(final Button b){
        b.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                b.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), null)));
            }
        });
        b.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                b.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(5), null)));
            }
        });
    }
    private void setButtonGoAction(final Button b, final Stage stage, final Scene scene){
        b.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setScene(scene);
            }
        });
    }
    //metodo di inizializzazione
    public void start(final Stage stage) throws IOException, ClassNotFoundException{
        //preparazione stage
        prepareStage(stage);
        //preparazione sessione
        s = new Session();
        //preparazione sfondo
        Background imageBackground = new Background(new BackgroundImage(new Image("https://media.istockphoto.com/vectors/digital-technology-gaming-abstract-background-vector-id1164222265?k=20&m=1164222265&s=612x612&w=0&h=MVArEQyhxQZu4O_qQtESnXbPiZMD1Hl6cyNaDdH3bAc="), null, null, null, BackgroundSize.DEFAULT));
        //preparazione root
        VBox rootLogin=prepareRoot(imageBackground);
        VBox rootRegister=prepareRoot(imageBackground);
        VBox rootMainMenu=prepareRoot(imageBackground);
        VBox rootUserPage=prepareRoot(imageBackground);
        VBox rootUserReviews=prepareRoot(imageBackground);
        VBox rootUserAdvice=prepareRoot(imageBackground);
        VBox rootGames=prepareRoot(imageBackground);
        VBox rootGameReviews=prepareRoot(imageBackground);
        VBox rootMakeReview=prepareRoot(imageBackground);
        VBox rootReviews=prepareRoot(imageBackground);
        VBox rootAdvice=prepareRoot(imageBackground);
        VBox rootMakeAdvise=prepareRoot(imageBackground);
        VBox rootAdviseResult=prepareRoot(imageBackground);
        VBox rootMakeAdviseResult=prepareRoot(imageBackground);
        VBox rootInsertGame=prepareRoot(imageBackground);
        //preparazione scene
        login = new Scene(rootLogin);
        register = new Scene(rootRegister);
        prepareRegister(stage);
        mainMenu = new Scene(rootMainMenu);
        userPage = new Scene(rootUserPage);
        userReviews = new Scene(rootUserReviews);
        userAdvice = new Scene(rootUserAdvice);
        games = new Scene(rootGames);
        gameReviews = new Scene(rootGameReviews);
        makeReview = new Scene(rootMakeReview);
        reviews = new Scene(rootReviews);
        advice = new Scene(rootAdvice);
        makeAdvise = new Scene(rootMakeAdvise);
        prepareMakeAdvise(stage);
        adviseResult = new Scene(rootAdviseResult);
        makeAdviseResult = new Scene(rootMakeAdviseResult);
        insertGame = new Scene(rootInsertGame);
        prepareInsertGame(stage);
        prepareLogin(stage);
        stage.setScene(login);
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }

}