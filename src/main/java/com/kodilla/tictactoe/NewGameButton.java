package com.kodilla.tictactoe;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class NewGameButton extends Button {

    public NewGameButton() {
        setTextFill(Color.web("#000000"));
        setStyle("-fx-background-color: #ADB3BC;");
        setFont(new Font("Arial", 30));
        minHeight(160);
        minWidth(180);
        setText("New Game");
        setOnMouseClicked(event -> Controller.getInstance().newGame(this));
    }
}
