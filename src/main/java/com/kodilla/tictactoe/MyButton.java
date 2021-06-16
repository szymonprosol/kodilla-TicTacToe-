package com.kodilla.tictactoe;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MyButton extends Rectangle {

    private static final int SIZE = 200;
    private final int col;
    private final int row;
    private Character myChar;

    public MyButton(int col, int row) {
        this.col = col;
        this.row = row;
        setFill(Color.web("#ADB3BC"));
        setHeight(SIZE);
        setWidth(SIZE);
        setOnMouseClicked(event -> Controller.getInstance().click(this));
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setChar(Character myChar) {
        this.myChar = myChar;
    }

    public Character getMyChar() {
        return myChar;
    }
}
