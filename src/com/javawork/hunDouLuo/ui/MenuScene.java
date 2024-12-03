package com.javawork.hunDouLuo.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class MenuScene {
    private Stage stage;

    public MenuScene(Stage stage) {
        this.stage = stage;
    }

    public void showMenu() {
        AnchorPane pane = new AnchorPane();
        // 菜单标题
        Text title = new Text("菜单");
        title.setStyle("-fx-font-size: 24; -fx-fill: white;");
        AnchorPane.setTopAnchor(title, 50.0);
        AnchorPane.setLeftAnchor(title, 200.0);
        // 创建菜单按钮
        Button startGameButton = new Button("开始游戏");
        startGameButton.setStyle("-fx-font-size: 16; -fx-background-color: lightblue;");
        startGameButton.setOnAction(e -> startGame());
        Button exitButton = new Button("退出游戏");
        exitButton.setStyle("-fx-font-size: 16; -fx-background-color: lightblue;");
        exitButton.setOnAction(e -> stage.close());
        // 布局按钮
        AnchorPane.setTopAnchor(startGameButton, 100.0);
        AnchorPane.setLeftAnchor(startGameButton, 200.0);

        AnchorPane.setTopAnchor(exitButton, 150.0);
        AnchorPane.setLeftAnchor(exitButton, 200.0);
        pane.getChildren().addAll(title, startGameButton, exitButton);
        // 设置场景
        Scene scene = new Scene(pane, 900, 672);
        scene.getStylesheets().add(App.class.getResource("/style/style.css").toExternalForm());
        stage.setScene(scene);
    }

    private void startGame() {
        // 调用魂斗罗游戏的启动逻辑
        System.out.println("启动魂斗罗游戏...");
        Thread game = new Thread(new MainFrame());
        game.start();
        App.getPrimaryStage().hide();
        try {
            game.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            App.gameEnd(MainFrame.username,MainFrame.score);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
