package com.kodilla.tictactoe;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;


public class TicTacToe extends Application implements Serializable {

    private Label status = new Label();
    public String playerName;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // popup
        GridPane grid1 = new GridPane();
        grid1.setPadding(new Insets(10, 10, 10, 10));
        grid1.setVgap(5);
        grid1.setHgap(5);
        final TextField name = new TextField();
        name.setPromptText("Enter your name.");
        name.setPrefColumnCount(10);
        playerName = name.getText().toLowerCase();
        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 1, 0);
        grid1.getChildren().add(submit);
        Button clear = new Button("Clear");
        GridPane.setConstraints(clear, 1, 1);
        grid1.getChildren().add(clear);
        GridPane.setConstraints(name, 0, 0);
        grid1.getChildren().add(name);

        //Setting an action for the Submit button
        submit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                boolean isPlayer = false;
                for (Player player : Controller.getInstance().getPlayers()){
                    if (player.getName() == playerName) {
                        isPlayer = true;
                        break;
                    }
                }
                if (isPlayer){
                    System.out.println("nazwa gracza jest zajeta");
                } else {
                    Player player = new Player(playerName);
                    Controller.getInstance().addPlayers(player);
                    primaryStage.close();
                    game();
                }
            }
        });

        //Setting an action for the Clear button
        clear.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                name.clear();
            }
        });

        Scene scene1 = new Scene(grid1, 250, 150, Color.BLUE);
        //Stage stage = new Stage();
        primaryStage.setTitle("LOG ON");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    public void game() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        status.setTextFill(Color.web("#BBB"));
        status.setFont(new Font("Arial", 24));

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                MyButton button = new MyButton(col, row);
                GridPane.setConstraints(button, col, row);
                grid.getChildren().add(button);
                Controller.getInstance().addButton(button);
            }
        }

        // load game button
        LoadGameButton loadGameButton = new LoadGameButton();
        GridPane.setConstraints(loadGameButton, 1, 3);
        grid.getChildren().add(loadGameButton);

        // save game button
        SaveGameButton saveGameButton = new SaveGameButton();
        GridPane.setConstraints(saveGameButton, 0, 3);
        grid.getChildren().add(saveGameButton);

        // ranking button
        RankingButton rankingButton = new RankingButton();
        GridPane.setConstraints(rankingButton, 2, 3);
        grid.getChildren().add(rankingButton);

        // new game button
        NewGameButton nGmButton = new NewGameButton();
        GridPane.setConstraints(nGmButton, 4,2 );
        grid.getChildren().add(nGmButton);

        // difficulty level button
        SwitchButton sw = new SwitchButton();
        GridPane.setConstraints(sw, 3, 2);
        grid.getChildren().add(sw);
        Controller.getInstance().addSwitchButton(sw);
        // choose X button
        Choose_X chooseX = new Choose_X();
        GridPane.setConstraints(chooseX, 3, 0);
        grid.getChildren().add(chooseX);
        Controller.getInstance().addChoose_X(chooseX);
        // choose O button
        Choose_O chooseO = new Choose_O();
        GridPane.setConstraints(chooseO, 4, 0);
        grid.getChildren().add(chooseO);
        Controller.getInstance().addChoose_O(chooseO);
        // status label
        status = Controller.getInstance().getStatus();
        GridPane.setConstraints(status, 3, 1);
        grid.getChildren().add(status);

        Scene scene = new Scene(grid, 950, 690, Color.BLUE);
        scene.getRoot().setStyle("-fx-font-family: 'serif'");
        Stage stage = new Stage();
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}