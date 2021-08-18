package com.kodilla.tictactoe;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class NewGameButton extends Button {

    public NewGameButton() {
        setTextFill(Color.web("#000000"));
        setStyle("-fx-border-color:transparent");
        setFont(new Font("Arial", 20));
        minHeight(5);
        minWidth(5);
        setText("New Game");
        setOnMouseClicked(event -> Controller.getInstance().newGame(this));
    }
}
