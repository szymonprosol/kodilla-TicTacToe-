package com.kodilla.tictactoe;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class SwitchButton extends Button {

    public SwitchButton() {
        ToggleButton toggleButton1 = new ToggleButton();
        setTextFill(Color.web("#000000"));
        setFont(new Font("Arial", 24));
        minHeight(30);
        minWidth(30);
        setText("HARD");
        setOnMouseClicked(event -> Controller.getInstance().setDifficultyLevel());
    }
}