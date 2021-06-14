package com.kodilla.tictactoe;

import javafx.scene.shape.Rectangle;

public class MyButton extends Rectangle {

    private static final int SIZE = 200;
    private final int col;
    private final int row;

    public MyButton(int col, int row) {
        this.col = col;
        this.row = row;
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
}
