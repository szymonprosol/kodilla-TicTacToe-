package com.kodilla.tictactoe;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.Serializable;

public class Choose_O extends Button implements Serializable {

    public Choose_O() {
        this.setTextFill(Color.web("#000000"));
        setStyle("-fx-border-color:transparent");
        this.setFont(new Font("Arial", 50));
        this.minHeight(60);
        this.minWidth(60);
        this.setText("O");
        setOnMouseClicked(event -> Controller.getInstance().setOChar(this));
    }
}