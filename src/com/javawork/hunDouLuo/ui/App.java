package com.javawork.hunDouLuo.ui;

import entity.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import sqlOperator.Manager;

import java.sql.SQLException;
import java.util.List;

public class App extends Application {

    public static LoginSoundThread lgsound;
    private static Stage primaryStage;
    private static Stage gameEndStage;
    private static Stage rankingStage;
    private static Stage lstage;
    public static boolean loginSuccessful = false;

    private static String username;

    public static String getUsername() {
        return username;
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        lgsound = new LoginSoundThread();
        App.primaryStage = primaryStage;
        primaryStage.setTitle("LOGIN");
        primaryStage.setResizable(false);
        showLoginUI();

        // 创建第二个窗口，但不显示
        gameEndStage = new Stage();
        gameEndStage.setTitle("Game Over");


    }

    public static void showLoginUI() {
        Platform.runLater(() -> {
            primaryStage.setTitle("LOGIN");

            Label usernameLabel = new Label("USERNAME:");
            usernameLabel.getStyleClass().add("label1");
            TextField usernameField = new TextField();
            usernameField.getStyleClass().add("field1");

            // 创建密码标签和输入框
            Label passwordLabel = new Label("PASSWORD:");
            passwordLabel.getStyleClass().add("label1");
            PasswordField passwordField = new PasswordField();
            passwordField.getStyleClass().add("field1");
            Button loginButton = new Button();
            loginButton.getStyleClass().add("button");
            loginButton.setText("LOGIN");

            Button rankButton = new Button("RANK");
            rankButton.getStyleClass().add("button");
            rankButton.setOnAction(e -> {
                try {
                    showRankingUI();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });





            loginButton.setOnAction(e -> {
                username = usernameField.getText();
                String password = passwordField.getText();
                try {
                    handleLogin(username, password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            Button registerButton = new Button();
            registerButton.getStyleClass().add("button");

            registerButton.setText("ENROLL");

            registerButton.setOnAction(e -> showRegisterUI());

            // 创建布局并添加控件
            AnchorPane anchorPane = new AnchorPane();

            // 添加控件到 AnchorPane 并设置位置
            AnchorPane.setTopAnchor(usernameLabel, 315.0);
            AnchorPane.setLeftAnchor(usernameLabel, 200.0);
            AnchorPane.setRightAnchor(usernameLabel, 200.0);

            AnchorPane.setTopAnchor(usernameField, 340.0);
            AnchorPane.setLeftAnchor(usernameField, 200.0);
            AnchorPane.setRightAnchor(usernameField, 200.0);

            AnchorPane.setTopAnchor(passwordLabel, 385.0);
            AnchorPane.setLeftAnchor(passwordLabel, 200.0);
            AnchorPane.setRightAnchor(passwordLabel, 200.0);

            AnchorPane.setTopAnchor(passwordField, 410.0);
            AnchorPane.setLeftAnchor(passwordField, 200.0);
            AnchorPane.setRightAnchor(passwordField, 200.0);

            AnchorPane.setTopAnchor(loginButton, 450.0);
            AnchorPane.setLeftAnchor(loginButton, 200.0);
            AnchorPane.setRightAnchor(loginButton, 200.0);

            AnchorPane.setTopAnchor(registerButton, 490.0);
            AnchorPane.setLeftAnchor(registerButton, 200.0);
            AnchorPane.setRightAnchor(registerButton, 200.0);

            AnchorPane.setTopAnchor(rankButton, 530.0);
            AnchorPane.setLeftAnchor(rankButton, 200.0);
            AnchorPane.setRightAnchor(rankButton, 200.0);


            anchorPane.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, registerButton, rankButton);

            // 创建场景并设置到舞台
            Scene scene = new Scene(anchorPane, 500, 600);
            scene.getStylesheets().add(App.class.getResource("/style/style.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();

            if(!lgsound.isplaying) {

                new Thread(lgsound).start();
            }

        });
    }

    public static void showRegisterUI() {
             primaryStage.setTitle("ENROLL");
            Label usernameLabel = new Label("USERNAME:");
            usernameLabel.getStyleClass().add("label1");
            TextField usernameField = new TextField();
            usernameField.getStyleClass().add("field1");
            // 创建密码标签和输入框
            Label passwordLabel = new Label("PASSWORD:");
            passwordLabel.getStyleClass().add("label1");

            PasswordField passwordField = new PasswordField();
            passwordField.getStyleClass().add("field1");
            Button registerButton = new Button();
            registerButton.getStyleClass().add("button");

            registerButton.setText("ENROLL");

            Button backButton = new Button("BACK");
            backButton.getStyleClass().add("button");

            backButton.setOnAction(e -> {
                showLoginUI();
            });


            registerButton.setOnAction(e -> {
                String username = usernameField.getText();
                String password = passwordField.getText();
                try {
                    handleRegister(username, password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            AnchorPane anchorPane = new AnchorPane();
            // 创建布局并添加控件
            AnchorPane.setTopAnchor(usernameLabel, 315.0);
            AnchorPane.setLeftAnchor(usernameLabel, 200.0);
            AnchorPane.setRightAnchor(usernameLabel, 200.0);

            AnchorPane.setTopAnchor(usernameField, 340.0);
            AnchorPane.setLeftAnchor(usernameField, 200.0);
            AnchorPane.setRightAnchor(usernameField, 200.0);

            AnchorPane.setTopAnchor(passwordLabel, 385.0);
            AnchorPane.setLeftAnchor(passwordLabel, 200.0);
            AnchorPane.setRightAnchor(passwordLabel, 200.0);

            AnchorPane.setTopAnchor(passwordField, 410.0);
            AnchorPane.setLeftAnchor(passwordField, 200.0);
            AnchorPane.setRightAnchor(passwordField, 200.0);

            AnchorPane.setTopAnchor(registerButton, 470.0);
            AnchorPane.setLeftAnchor(registerButton, 200.0);
            AnchorPane.setRightAnchor(registerButton, 200.0);

            AnchorPane.setTopAnchor(backButton, 530.0);
            AnchorPane.setLeftAnchor(backButton, 200.0);
            AnchorPane.setRightAnchor(backButton, 200.0);

            anchorPane.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField,  registerButton, backButton);

            // 创建场景并设置到舞台
            Scene scene = new Scene(anchorPane, 500, 600);
            scene.getStylesheets().add(App.class.getResource("/style/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

    }



    public  static void showRankingUI() throws SQLException {
        primaryStage.setTitle("TOP 5 PLAYER");

        GridPane header = new GridPane();
        header.setAlignment(Pos.CENTER);
        header.setHgap(10);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(140);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(10);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPrefWidth(60);
        header.getColumnConstraints().addAll(col1, col2, col3);

        Label usernameHeader = new Label("USERNAME");
        usernameHeader.getStyleClass().add("label1");


        Label scoreHeader = new Label("SCORE");
        scoreHeader.getStyleClass().add("label1");


        header.add(usernameHeader, 0, 0);
        header.add(createVerticalSeparator(), 1, 0);
        header.add(scoreHeader, 2, 0);



        VBox vbox = new VBox(10); // 设置 VBox 的间距为 10
        vbox.setPadding(new Insets(10)); // 设置 VBox 的内边距
        vbox.getStyleClass().add("root");

        // 创建一个 Region 用于占据上半部分的空间
        Region spacer = new Region();
        spacer.setPrefHeight(350);

        VBox bottomBox = new VBox(10);
        bottomBox.setAlignment(Pos.CENTER);

        bottomBox.getChildren().add(header);

        Button backButton = new Button("BACK");
        backButton.getStyleClass().add("button");

        backButton.setOnAction(e -> {
            showLoginUI();
        });

        VBox buttonBox = new VBox(backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        // 加载用户数据
        loadUserData(bottomBox);

        vbox.getChildren().addAll(spacer, bottomBox,buttonBox);

        Scene scene = new Scene(vbox, 500, 600);
        scene.getStylesheets().add(App.class.getResource("/style/rank.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private static Line createVerticalSeparator() {
        Line separator = new Line(0, 0, 0, 20);
        separator.setStroke(Color.color( 0.501, 0.3398, 0));
        return separator;
    }

    private static void loadUserData(VBox bottomBox) {
        Manager manager = new Manager();
        try {
            List<User> topUsers = manager.getTop5UsersByScore();
            for (User user : topUsers) {
                GridPane row = new GridPane();
                row.setAlignment(Pos.CENTER);
                row.setHgap(10);

                ColumnConstraints col1 = new ColumnConstraints();
                col1.setPrefWidth(140);
                ColumnConstraints col2 = new ColumnConstraints();
                col2.setPrefWidth(10);
                ColumnConstraints col3 = new ColumnConstraints();
                col3.setPrefWidth(60);
                row.getColumnConstraints().addAll(col1, col2, col3);

                Label usernameLabel = new Label(user.getUsername());
                Label scoreLabel = new Label(String.valueOf(user.getScore()));

                row.add(usernameLabel, 0, 0);
                row.add(createVerticalSeparator(), 1, 0);
                row.add(scoreLabel, 2, 0);

                bottomBox.getChildren().add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void handleLogin(String username, String password) throws SQLException {
        // 处理登录逻辑
        Manager sqlManager = new Manager();
        boolean findflag = sqlManager.userfind(username, password);
        if (findflag) {
            loginSuccessful = true;

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("login success! 登录成功!");
            alert.setHeaderText(null);
            alert.setContentText("welcome 欢迎, " + username + "!");
            if (lgsound != null) {
                lgsound.stopLoginSound();
            }
            MenuScene menuScene = new MenuScene(primaryStage);
            menuScene.showMenu();
//            alert.showAndWait();
//            primaryStage.hide();

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("login failed 登录失败");
            alert.setHeaderText(null);
            alert.setContentText("wrong username or password plz try again 用户名或密码错误，请重试。");
            alert.showAndWait();
        }
    }

    private static void handleRegister(String username, String password) throws SQLException {
        // 处理注册逻辑
        Manager sqlManager = new Manager();
        if (sqlManager.userfind(username)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("enroll failed 注册失败");
            alert.setHeaderText(null);
            alert.setContentText("existing username  用户名已存在，请选择其他用户名。");
            alert.showAndWait();
        } else {
            boolean state  =  sqlManager.userinsert(username, password, 0);
            if(state) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("enroll success 注册成功");
                alert.setHeaderText(null);
                alert.setContentText("plz login 注册成功，请登录。");
                alert.showAndWait();
                showLoginUI();
            }else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("enroll failed 注册失败");
                alert.setHeaderText(null);
                alert.setContentText("nullException 用户名或者密码不能为空");
                alert.showAndWait();
            }
        }
    }
    public static void showGameEndUI(String username, int score, int state) {
        Platform.runLater(() -> {

            Label label = new Label(username + "!!!!!!!!" + "\nyour score is :" + score);

            if (state == 3) {
                label = new Label(username + "!!!!!!!!" + "\nyour score is :" + score + "\ndidn't break your best \n need more effort!!!!!!!!!!!!!!!");
            }
            if (state == 1) {
                label = new Label(username + "!!!!!!!!" + "\nyour score is :" + score + "\ncongratulation! your personal best born!");
            }


            label.setStyle("-fx-font-size:24;-fx-text-fill:rgb(34,158,241);");
            StackPane root = new StackPane();
            root.getChildren().add(label);

            Scene scene = new Scene(root, 400, 300);
            gameEndStage.setScene(scene);
            gameEndStage.show();
        });
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Stage getGameEndStage() {
        return gameEndStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    // 定义 gameStartfx 方法
    public static String gameStartfx() {
        new Thread(() -> Application.launch(App.class)).start();
        while (App.getPrimaryStage() == null) {
            try {
                Thread.sleep(100); // 等待100毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Platform.runLater(() -> App.showLoginUI());
        // 等待login成功
        while (!App.loginSuccessful) {
            try {
                Thread.sleep(100); // 等待100毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return App.getUsername();
    }

    // 定义 gameEnd 方法
    public static void gameEnd(String username, int score) throws SQLException {


        Manager sqlmanager = new Manager();
        int stateofupdate = sqlmanager.userUpdateScore(username, score);
            App.showGameEndUI(username, score, stateofupdate);

    }
}