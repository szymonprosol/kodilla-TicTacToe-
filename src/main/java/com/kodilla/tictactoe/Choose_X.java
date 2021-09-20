package com.kodilla.tictactoe;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Choose_X extends Button {

    public Choose_X() {
        setTextFill(Color.web("#000000"));
        setStyle("-fx-border-color:transparent");
        setFont(new Font("Arial", 50));
        minHeight(60);
        minWidth(60);
        setText("X");
        setOnMouseClicked(event -> Controller.getInstance().setXChar(this));
    }
}
