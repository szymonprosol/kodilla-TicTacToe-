package com.kodilla.tictactoe;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Choose_X extends Button {

    public Choose_X() {
        this.setTextFill(Color.web("#000000"));
        this.setStyle("-fx-background-color: #ADB3BC;");
        this.setFont(new Font("Arial", 50));
        this.minHeight(60);
        this.minWidth(60);
        this.setText("X");
        setOnMouseClicked(event -> Controller.getInstance().setXChar(this));
    }
}
