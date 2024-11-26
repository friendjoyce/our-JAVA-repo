package com.javawork.hunDouLuo.ui.fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class LoginApp extends Application {

    private static Stage primaryStage;
    public static boolean loginSuccessful = false;
    private static Map<String, String> userDatabase = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        LoginApp.primaryStage = primaryStage;
        primaryStage.setTitle("登录界面");

    }

    public static void showUI() {
        Platform.runLater(() -> {
            // 创建用户名标签和输入框
            Label usernameLabel = new Label("用户名:");
            TextField usernameField = new TextField();

            // 创建密码标签和输入框
            Label passwordLabel = new Label("密码:");
            PasswordField passwordField = new PasswordField();

            // 创建登录按钮
            Button loginButton = new Button("登录");
            loginButton.setOnAction(e -> {
                String username = usernameField.getText();
                String password = passwordField.getText();
                handleLogin(username, password);
            });

            // 创建注册按钮
            Button registerButton = new Button("注册");
            registerButton.setOnAction(e -> showRegisterUI());

            // 创建布局并添加控件
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.add(usernameLabel, 0, 0);
            gridPane.add(usernameField, 1, 0);
            gridPane.add(passwordLabel, 0, 1);
            gridPane.add(passwordField, 1, 1);
            gridPane.add(loginButton, 1, 2);
            gridPane.add(registerButton, 1, 3);

            // 创建场景并设置到舞台
            Scene scene = new Scene(gridPane, 300, 200);
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

            // 创建注册按钮
            Button registerButton = new Button("注册");
            registerButton.setOnAction(e -> {
                String username = usernameField.getText();
                String password = passwordField.getText();
                handleRegister(username, password);
            });

            // 创建布局并添加控件
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.add(usernameLabel, 0, 0);
            gridPane.add(usernameField, 1, 0);
            gridPane.add(passwordLabel, 0, 1);
            gridPane.add(passwordField, 1, 1);
            gridPane.add(registerButton, 1, 2);

            // 创建场景并设置到舞台
            Scene scene = new Scene(gridPane, 300, 200);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }

    private static void handleLogin(String username, String password) {
        // 处理登录逻辑
        if (userDatabase.containsKey(username) && userDatabase.get(username).equals(password)) {
            loginSuccessful = true;
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("登录成功");
            alert.setHeaderText(null);
            alert.setContentText("欢迎, " + username + "!");
            alert.showAndWait();
            primaryStage.close();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("登录失败");
            alert.setHeaderText(null);
            alert.setContentText("用户名或密码错误，请重试。");
            alert.showAndWait();
        }
    }

    private static void handleRegister(String username, String password) {
        // 处理注册逻辑
        if (userDatabase.containsKey(username)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("注册失败");
            alert.setHeaderText(null);
            alert.setContentText("用户名已存在，请选择其他用户名。");
            alert.showAndWait();
        } else {
            userDatabase.put(username, password);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("注册成功");
            alert.setHeaderText(null);
            alert.setContentText("注册成功，请登录。");
            alert.showAndWait();
            showUI();
        }
    }

    public static void main(String[] args) {
        launch(args);

        // 等待登录成功
        while (!loginSuccessful) {
            try {
                Thread.sleep(100); // 等待100毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 登录成功后继续执行下一步操作
        System.out.println("登录成功，继续执行下一步操作...");
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}