package com.javawork.hunDouLuo.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sqlOperator.Manager;

import java.sql.SQLException;

public class App extends Application {

    public static LoginSoundThread lgsound;
    private static Stage primaryStage;
    private static Stage gameEndStage;
    public static boolean loginSuccessful = false;

    private static String username;

    public static String getUsername() {
        return username;
    }

    @Override
    public void start(Stage primaryStage) {
        App.primaryStage = primaryStage;
        primaryStage.setTitle("登录界面");
        primaryStage.setResizable(false);
        showLoginUI();

        // 创建第二个窗口，但不显示
        gameEndStage = new Stage();
        gameEndStage.setTitle("Game Over");
    }

    public static void showLoginUI() {
        Platform.runLater(() -> {

//            Label titleLabel = new Label("全球排名！\n 梦回经典--魂斗罗");
//            titleLabel.getStyleClass().add("title1");
//            titleLabel.setText("全球排名！\n 梦回经典--魂斗罗");
            // 创建username标签和输入框
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

            anchorPane.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, registerButton);

            // 创建场景并设置到舞台
            Scene scene = new Scene(anchorPane, 500, 600);
            scene.getStylesheets().add(App.class.getResource("/style/style.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();

            lgsound=new LoginSoundThread();
            new Thread(lgsound).start();


        });
    }

    public static void showRegisterUI() {

            // 创建username标签和输入框

//            Label titleLabel = new Label("全球排名！\n 梦回经典--魂斗罗");
//            titleLabel.getStyleClass().add("title1");
//            titleLabel.setText("全球排名！\n 梦回经典--魂斗罗");
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

            anchorPane.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField,  registerButton);

            // 创建场景并设置到舞台
            Scene scene = new Scene(anchorPane, 500, 600);
            scene.getStylesheets().add(App.class.getResource("/style/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

    }

    private static void handleLogin(String username, String password) throws SQLException {
        // 处理登录逻辑
        Manager sqlManager = new Manager();
        boolean findflag = sqlManager.userfind(username, password);
        if (findflag) {
            loginSuccessful = true;

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("登录成功");
            alert.setHeaderText(null);
            alert.setContentText("欢迎, " + username + "!");
            if (lgsound != null) {
                lgsound.stopLoginSound();
            }
            MenuScene menuScene = new MenuScene(primaryStage);
            menuScene.showMenu();
//            alert.showAndWait();
//            primaryStage.hide();

        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("登录失败");
            alert.setHeaderText(null);
            alert.setContentText("用户名或密码错误，请重试。");
            alert.showAndWait();
        }
    }

    private static void handleRegister(String username, String password) throws SQLException {
        // 处理注册逻辑
        if(username==null || password==null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("错误");
            alert.setHeaderText(null);
            alert.setContentText("不能为空！");
            alert.showAndWait();
        }
        Manager sqlManager = new Manager();
        if (sqlManager.userfind(username)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("注册失败");
            alert.setHeaderText(null);
            alert.setContentText("用户名已存在，请选择其他用户名。");
            alert.showAndWait();
        } else {
            sqlManager.userinsert(username, password, 0);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("注册成功");
            alert.setHeaderText(null);
            alert.setContentText("注册成功，请登录。");
            alert.showAndWait();
            showLoginUI();
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
            App.showGameEndUI(username, score, stateofupdate); // 示例参数

    }
}