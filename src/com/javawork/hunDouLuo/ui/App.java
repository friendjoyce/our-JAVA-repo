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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sqlOperator.Manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class App extends Application {

    private static Stage primaryStage;
    private static Stage gameEndStage;
    public static boolean loginSuccessful = false;
    private static String password;
    private static String username;

    public static String getUsername() {
        return username;
    }

    @Override
    public void start(Stage primaryStage) {
        App.primaryStage = primaryStage;
        primaryStage.setTitle("登录界面");
        showLoginUI();

        // 创建第二个窗口，但不显示
        gameEndStage = new Stage();
        gameEndStage.setTitle("Game Over");
    }

    public static void showLoginUI() {
        Platform.runLater(() -> {
            // 创建用户名标签和输入框
            Label usernameLabel = new Label("用户名:");
            TextField usernameField = new TextField();

            // 创建密码标签和输入框
            Label passwordLabel = new Label("密码:");
            PasswordField passwordField = new PasswordField();

            Button loginButton = new Button();
            loginButton.getStyleClass().add("button");

            loginButton.setText("登录");

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

            registerButton.setText("注册");

            registerButton.setOnAction(e -> showRegisterUI());

            // 创建布局并添加控件
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setAlignment(Pos.CENTER);
            gridPane.add(usernameLabel, 0, 0);
            gridPane.add(usernameField, 0, 1);
            gridPane.add(passwordLabel, 0, 2);
            gridPane.add(passwordField, 0, 3);
            gridPane.add(loginButton, 0, 4);
            gridPane.add(registerButton, 0, 5);

            GridPane.setValignment(usernameLabel, VPos.CENTER);
            GridPane.setHalignment(usernameLabel, HPos.CENTER);
            GridPane.setHalignment(usernameField, HPos.CENTER);
            GridPane.setValignment(usernameField, VPos.CENTER);
            GridPane.setHalignment(passwordLabel, HPos.CENTER);
            GridPane.setValignment(passwordLabel, VPos.CENTER);
            GridPane.setHalignment(passwordField, HPos.CENTER);
            GridPane.setValignment(passwordField, VPos.CENTER);
            GridPane.setHalignment(loginButton, HPos.CENTER);
            GridPane.setValignment(loginButton, VPos.CENTER);
            GridPane.setHalignment(registerButton, HPos.CENTER);
            GridPane.setValignment(registerButton, VPos.CENTER);

            // 创建 StackPane 并将 GridPane 添加到其中
            StackPane stackPane = new StackPane(gridPane);
            stackPane.setAlignment(Pos.CENTER);

            // 创建场景并设置到舞台
            Scene scene = new Scene(stackPane, 300, 200);
            scene.getStylesheets().add(App.class.getResource("/style/button.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }

    public static void showRegisterUI() {
        Platform.runLater(() -> {
            // 创建用户名标签和输入框
            Label usernameLabel = new Label("用户名:");
            TextField usernameField = new TextField();

            // 创建密码标签和输入框
            Label passwordLabel = new Label("密码:");
            PasswordField passwordField = new PasswordField();

            Button registerButton = new Button();
            registerButton.getStyleClass().add("button");

            registerButton.setText("注册");




            registerButton.setOnAction(e -> {
                String username = usernameField.getText();
                String password = passwordField.getText();
                try {
                    handleRegister(username, password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });

            // 创建布局并添加控件
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setAlignment(Pos.CENTER);
            gridPane.add(usernameLabel, 0, 0);
            gridPane.add(usernameField, 0, 1);
            gridPane.add(passwordLabel, 0, 2);
            gridPane.add(passwordField, 0, 3);

            gridPane.add(registerButton, 0, 4);

            GridPane.setValignment(usernameLabel, VPos.CENTER);
            GridPane.setHalignment(usernameField, HPos.CENTER);
            GridPane.setValignment(usernameField, VPos.CENTER);
            GridPane.setHalignment(passwordLabel, HPos.CENTER);
            GridPane.setValignment(passwordLabel, VPos.CENTER);
            GridPane.setHalignment(passwordField, HPos.CENTER);
            GridPane.setValignment(passwordField, VPos.CENTER);

            GridPane.setHalignment(registerButton, HPos.CENTER);
            GridPane.setValignment(registerButton, VPos.CENTER);


            StackPane stackPane = new StackPane(gridPane);
            stackPane.setAlignment(Pos.CENTER);

            // 创建场景并设置到舞台
            Scene scene = new Scene(stackPane, 300, 200);
            scene.getStylesheets().add(App.class.getResource("/style/button.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        });
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
            alert.showAndWait();
            primaryStage.hide();
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
        // 等待登录成功
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