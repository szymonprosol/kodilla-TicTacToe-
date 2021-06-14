package com.kodilla.tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TicTacToe extends Application {

    private Label status = new Label("Twój ruch");

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        status.setTextFill(Color.web("#BBB"));
        status.setFont(new Font("Arial", 24));

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                MyButton button = new MyButton(col, row);
                grid.add(button, col, row, 1, 1);
                Controller.getInstance().addButton(button);
            }
        }

        grid.add(status, 3, 1, 1, 1);

        Scene scene = new Scene(grid, 900, 620, Color.BLUE);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}