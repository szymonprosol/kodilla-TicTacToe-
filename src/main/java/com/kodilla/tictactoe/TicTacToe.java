package com.kodilla.tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TicTacToe extends Application {

    private Image imageback = new Image("file:src/main/resources/tlo2.jpeg");

    private Label status = new Label("Twój ruch");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        status.setTextFill(Color.web("#BBB"));
        status.setFont(new Font("Arial", 24));

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                MyButton button = new MyButton(col, row);
                grid.add(button,col,row,1,1);
                Controller.getInstance().addButton(button);
            }
        }

        grid.add(status,3,1,1,1);

        Scene scene = new Scene(grid, 900, 620, Color.BLACK);

        primaryStage.setTitle("Simons Project - kolko");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}