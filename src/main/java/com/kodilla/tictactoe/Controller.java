package com.kodilla.tictactoe;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static final Controller instance = new Controller();
    private List<MyButton> myButtons = new ArrayList<>();

    private Controller() {}

    public static Controller getInstance(){
        return instance;
    }

    void addButton(MyButton button) {
        myButtons.add(button);
    }

    public void click(MyButton button) {
        System.out.printf("Clicked col=%s, row=%s \n", button.getCol(), button.getRow());
        button.setFill(Color.RED);
    }
}
