package com.kodilla.tictactoe;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class SwitchButton extends Button {

    public SwitchButton() {
        ToggleButton toggleButton1 = new ToggleButton();
        setStyle("-fx-border-color:transparent");
        setFont(new Font("Arial", 20));
        minHeight(5);
        minWidth(5);
        setText("HARD");
        setOnMouseClicked(event -> Controller.getInstance().setDifficultyLevel());
    }
}