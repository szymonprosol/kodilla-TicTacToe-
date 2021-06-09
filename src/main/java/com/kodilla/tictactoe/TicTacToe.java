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
        //grid.setBackground(background);

        Button button1 = new Button();
        Button button2 = new Button();
        Button button3 = new Button();
        Button button4 = new Button();
        Button button5 = new Button();
        Button button6 = new Button();
        Button button7 = new Button();
        Button button8 = new Button();
        Button button9 = new Button();

        button1.setMinSize(200, 200);
        button2.setMinSize(200, 200);
        button3.setMinSize(200, 200);
        button4.setMinSize(200, 200);
        button5.setMinSize(200, 200);
        button6.setMinSize(200, 200);
        button7.setMinSize(200, 200);
        button8.setMinSize(200, 200);
        button9.setMinSize(200, 200);

        status.setTextFill(Color.web("#BBB"));
        status.setFont(new Font("Arial", 24));

        grid.add(button1, 0, 0, 1, 1);
        grid.add(button2, 0, 1, 1, 1);
        grid.add(button4, 1, 0, 1, 1);
        grid.add(button3, 0, 2, 1, 1);
        grid.add(button5, 1, 1, 1, 1);
        grid.add(button6, 1, 2, 1, 1);
        grid.add(button7, 2, 0, 1, 1);
        grid.add(button8, 2, 1, 1, 1);
        grid.add(button9, 2, 2, 1, 1);
        grid.add(status,3,1,1,1);

        Scene scene = new Scene(grid, 900, 620, Color.BLACK);

        primaryStage.setTitle("Simons Project - kolko");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}