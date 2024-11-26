package com.javawork.hunDouLuo.ui.fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class gameEndfx extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        gameEndfx.primaryStage = primaryStage;
        primaryStage.setTitle("you died!");
    }

    public static void showUI(int score) {
        Platform.runLater(() -> {
            Label label = new Label("your score is :"+score);
            StackPane root = new StackPane();
            root.getChildren().add(label);

            Scene scene = new Scene(root, 300, 200);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}