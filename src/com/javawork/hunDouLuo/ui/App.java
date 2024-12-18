package com.javawork.hunDouLuo.ui;

import entity.User;
import javafx.animation.ScaleTransition;
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
import javafx.util.Duration;
import sqlOperator.Manager;

import java.sql.SQLException;
import java.util.List;

import static com.javawork.hunDouLuo.ui.MenuScene.addHoverAnimation;

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
    public void start(Stage primaryStage) throws SQLException {
        App.primaryStage = primaryStage;
        primaryStage.setTitle("LOGIN");
        showLoginUI();

        // 在窗口关闭时调用 stop 方法
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Window closed");
            stop();  // 手动调用 stop 方法
        });

        // 创建第二个窗口，但不显示
        gameEndStage = new Stage();
        gameEndStage.setTitle("Game Over");
    }


    @Override
    public void stop() {
        System.out.println("Stop method is called!");  // 打印调试信息确认是否执行
        // 停止其他资源和线程
        if (lgsound != null && lgsound.isplaying) {
            lgsound.stopLoginSound();  // 停止登录音效播放
        }
        // 如果有其他需要清理的资源，可以在这里做
        // 例如数据库连接、文件操作等
        Platform.exit();  // 退出 JavaFX 应用
        System.exit(0);   // 强制退出程序，确保 JVM 退出
    }
    public static void showLoginUI() {
        lgsound = new LoginSoundThread();
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


            addHoverAnimation(registerButton);
            addHoverAnimation(loginButton);


            // 创建布局并添加控件
            AnchorPane anchorPane = new AnchorPane();

            // 添加控件到 AnchorPane 并设置位置
            AnchorPane.setTopAnchor(usernameLabel, 315.0);
            AnchorPane.setLeftAnchor(usernameLabel, 200.0);
            AnchorPane.setRightAnchor(usernameLabel, 200.0);

            AnchorPane.setTopAnchor(usernameField, 340.0);
            AnchorPane.setLeftAnchor(usernameField, 350.0);
            AnchorPane.setRightAnchor(usernameField, 350.0);

            AnchorPane.setTopAnchor(passwordLabel, 385.0);
            AnchorPane.setLeftAnchor(passwordLabel, 200.0);
            AnchorPane.setRightAnchor(passwordLabel, 200.0);

            AnchorPane.setTopAnchor(passwordField, 410.0);
            AnchorPane.setLeftAnchor(passwordField, 350.0);
            AnchorPane.setRightAnchor(passwordField, 350.0);

            AnchorPane.setTopAnchor(loginButton, 450.0);
            AnchorPane.setLeftAnchor(loginButton, 200.0);
            AnchorPane.setRightAnchor(loginButton, 200.0);

            AnchorPane.setTopAnchor(registerButton, 490.0);
            AnchorPane.setLeftAnchor(registerButton, 200.0);
            AnchorPane.setRightAnchor(registerButton, 200.0);




            anchorPane.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, registerButton);

            // 创建场景并设置到舞台
            Scene scene = new Scene(anchorPane, 900, 672);
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
        addHoverAnimation(registerButton);
        addHoverAnimation(backButton);

        AnchorPane anchorPane = new AnchorPane();
        // 创建布局并添加控件
        AnchorPane.setTopAnchor(usernameLabel, 315.0);
        AnchorPane.setLeftAnchor(usernameLabel, 200.0);
        AnchorPane.setRightAnchor(usernameLabel, 200.0);

        AnchorPane.setTopAnchor(usernameField, 340.0);
        AnchorPane.setLeftAnchor(usernameField, 350.0);
        AnchorPane.setRightAnchor(usernameField, 350.0);

        AnchorPane.setTopAnchor(passwordLabel, 385.0);
        AnchorPane.setLeftAnchor(passwordLabel, 200.0);
        AnchorPane.setRightAnchor(passwordLabel, 200.0);

        AnchorPane.setTopAnchor(passwordField, 410.0);
        AnchorPane.setLeftAnchor(passwordField, 350.0);
        AnchorPane.setRightAnchor(passwordField, 350.0);

        AnchorPane.setTopAnchor(registerButton, 470.0);
        AnchorPane.setLeftAnchor(registerButton, 200.0);
        AnchorPane.setRightAnchor(registerButton, 200.0);

        AnchorPane.setTopAnchor(backButton, 530.0);
        AnchorPane.setLeftAnchor(backButton, 200.0);
        AnchorPane.setRightAnchor(backButton, 200.0);

        anchorPane.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField,  registerButton, backButton);

        // 创建场景并设置到舞台
        Scene scene = new Scene(anchorPane, 900, 672);
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