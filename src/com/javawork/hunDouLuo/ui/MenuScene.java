package com.javawork.hunDouLuo.ui;

import entity.User;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import sqlOperator.Manager;

import java.sql.SQLException;
import java.util.List;

public class MenuScene {
    private Stage stage;

    public MenuScene(Stage stage) {
        this.stage = stage;
    }

    public void showMenu() {
        AnchorPane pane = new AnchorPane();
        // 菜单标题
        Text title = new Text("MENU 菜单");

        AnchorPane.setTopAnchor(title, 400.0);
        AnchorPane.setLeftAnchor(title, 395.0);
        title.getStyleClass().add("text1");

        // 标题动画
        ScaleTransition titleScaleEnter = new ScaleTransition(Duration.millis(500), title);
        titleScaleEnter.setFromX(1.0);
        titleScaleEnter.setFromY(1.0);
        titleScaleEnter.setToX(1.2);
        titleScaleEnter.setToY(1.2);
        titleScaleEnter.setCycleCount(1);
        titleScaleEnter.setAutoReverse(true);
        titleScaleEnter.play();

        // 创建菜单按钮
        Button startGameButton = new Button("START GAME 开始游戏");
        Button exitButton = new Button("EXIT 退出游戏");
        Button rankButton = new Button("RANK 排名");

        // 应用动画到按钮
        addHoverAnimation(startGameButton);
        addHoverAnimation(exitButton);
        addHoverAnimation(rankButton);

        // 按钮事件绑定
        startGameButton.setOnAction(e -> startGame());
        exitButton.setOnAction(e -> System.exit(0));
        rankButton.setOnAction(e -> {
            try {
                showRankingUI();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // 布局按钮
        AnchorPane.setTopAnchor(startGameButton, 500.0);
        AnchorPane.setLeftAnchor(startGameButton, 250.0);
        AnchorPane.setRightAnchor(startGameButton, 250.0);

        AnchorPane.setTopAnchor(exitButton, 550.0);
        AnchorPane.setLeftAnchor(exitButton, 250.0);
        AnchorPane.setRightAnchor(exitButton, 250.0);

        AnchorPane.setTopAnchor(rankButton, 600.0);
        AnchorPane.setLeftAnchor(rankButton, 250.0);
        AnchorPane.setRightAnchor(rankButton, 250.0);

        pane.getChildren().addAll(title, startGameButton, exitButton, rankButton);

        // 设置场景
        Scene scene = new Scene(pane, 900, 672);
        scene.getStylesheets().add(App.class.getResource("/style/style.css").toExternalForm());
        stage.setScene(scene);
    }

    public void showRankingUI() throws SQLException {
        stage.setTitle("TOP 5 PLAYER");

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

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getStyleClass().add("root");

        Region spacer = new Region();
        spacer.setPrefHeight(400);

        VBox bottomBox = new VBox(10);
        bottomBox.setAlignment(Pos.CENTER);

        bottomBox.getChildren().add(header);

        Button backButton = new Button("BACK");
        backButton.getStyleClass().add("button");
        addHoverAnimation(backButton);

        backButton.setOnAction(e -> {
            showMenu();
        });

        VBox buttonBox = new VBox(backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        loadUserData(bottomBox);

        vbox.getChildren().addAll(spacer, bottomBox, buttonBox);

        Scene scene = new Scene(vbox, 900, 672);
        scene.getStylesheets().add(App.class.getResource("/style/rank.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    private static Line createVerticalSeparator() {
        Line separator = new Line(0, 0, 0, 20);
        separator.setStroke(Color.color(0.501, 0.3398, 0));
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

    private void startGame() {
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
            App.gameEnd(MainFrame.username, MainFrame.score);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    static void addHoverAnimation(Button button) {
        ScaleTransition scaleEnter = new ScaleTransition(Duration.millis(200), button);
        scaleEnter.setToX(1.1);
        scaleEnter.setToY(1.1);

        ScaleTransition scaleExit = new ScaleTransition(Duration.millis(200), button);
        scaleExit.setToX(1.0);
        scaleExit.setToY(1.0);

        button.setOnMouseEntered(e -> scaleEnter.playFromStart());
        button.setOnMouseExited(e -> scaleExit.playFromStart());
    }
}