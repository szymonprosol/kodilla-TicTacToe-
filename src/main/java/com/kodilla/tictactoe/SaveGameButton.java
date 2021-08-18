package com.kodilla.tictactoe;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;

public class SaveGameButton extends Button {

    public SaveGameButton() {
        setTextFill(Color.web("#000000"));
        setStyle("-fx-border-color:transparent");
        setFont(new Font("Arial", 20));
        minHeight(5);
        minWidth(5);
        setText("Save Game");
        setOnMouseClicked(event -> Controller.getInstance().save());
    }
}
