package com.kodilla.tictactoe;

import javafx.scene.control.Label;

public class MyLabel extends Label {

    private static final int vSize = 15;
    private static final int hSize = 10;
    private final int col;
    private final int row;
    private String text;

    public MyLabel(int col, int row) {
        this.col = col;
        this.row = row;
        setHeight(hSize);
        setWidth(vSize);
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setTexts(String text) {
        this.text = text;
    }

    public String getTexts() {
        return text;
    }
}
