package com.kodilla.tictactoe;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Controller {

    private static final Random random = new Random();
    private static final Controller instance = new Controller();
    private List<MyButton> myButtons = new ArrayList<>();
    private char myChar = 0;
    private char compChar = 0;
    private char[][] array = new char[3][3];
    private int liczbaRuchow = 0;
    private Label status = new Label();
    private boolean gameOver = false;

    private Controller() {
    }

    public static Controller getInstance() {
        return instance;
    }

    void addButton(MyButton button) {
        myButtons.add(button);
    }

    public void click(MyButton button) {

        // validate move
        if (!validateMove(button)) {
            return;
        }

        // make player move
        makePlayerMove(button);

        // check if player won the game
        if (!gameOver) {
            checkIfPlayerWon();
        }

        // make computer move
        makeComputerMove();

        // check if computer won the game
        if (!gameOver) {
            checkIfComputerWon();
        }
    }

    public void setXChar(Choose_X chooseX) {
        if (liczbaRuchow != 0) {
            status.setText("Zakończ obecną rozgrywkę");
        } else {
            myChar = 'X';
            compChar = 'O';
        }
    }

    public void setOChar(Choose_O chooseO) {
        if (liczbaRuchow != 0) {
            status.setText("Zakończ obecną rozgrywkę");
        } else {
            myChar = 'O';
            compChar = 'X';
        }
    }

    public void newGame(NewGameButton nGmButton) {
        gameOver = false;
        myChar = 0;
        compChar = 0;
        array = new char[3][3];
        liczbaRuchow = 0;
        myButtons.stream()
                .forEach(el -> el.setChar(null));
        myButtons.stream()
                .forEach(el -> el.setFill(Color.web("#ADB3BC")));
        status.setText("Nowa gra - wybierz znak");
    }

    private void checkIfComputerWon() {
        if (endComp()) {
            status.setText("WYGRAŁ KOMPUTER!");
            gameOver = true;
        } else if (liczbaRuchow == 9) {
            status.setText("Brak możliwości ruchu - REMIS!");
            gameOver = true;
        } else {
            status.setText("Twój ruch!");
        }
    }

    private void makeComputerMove() {
        if (!gameOver) {
            List<MyButton> emptyButtons = myButtons.stream()
                    .filter(button -> button.getMyChar() == null)
                    .collect(Collectors.toList());
            MyButton button = emptyButtons.get(random.nextInt(emptyButtons.size()));

            array[button.getCol()][button.getRow()] = compChar;
            button.setFill(new ImagePattern(changeImage(compChar)));
            button.setChar(compChar);
            liczbaRuchow++;
        }
    }

    private void checkIfPlayerWon() {
        if (endPlayer()) {
            status.setText("GRATULACJE ZWYCIĘŻYŁEŚ!");
            gameOver = true;
        } else if (liczbaRuchow == 9) {
            status.setText("Brak możliwości ruchu - REMIS!");
            gameOver = true;
        } else {
            status.setText("Ruch komputera!");
        }
    }

    private void makePlayerMove(MyButton button) {
        if (!gameOver) {
            array[button.getCol()][button.getRow()] = myChar;
            button.setFill(new ImagePattern(changeImage(myChar)));
            button.setChar(myChar);
            liczbaRuchow++;
        }
    }

    private boolean validateMove(MyButton button) {
        if (array[button.getCol()][button.getRow()] != 0 && !gameOver) {
            status.setText("Wrong move!");
            return false;
        } else if (myChar == 0 || compChar == 0) {
            status.setText("Nie wybrano znaku");
            return false;
        }
        return true;
    }

    public static Image changeImage(char myChar) {
        Image x = new Image("file:src/main/resources/X.png");
        Image o = new Image("file:src/main/resources/O.png");
        if (myChar == 'X') {
            return x;
        } else {
            return o;
        }
    }

    public boolean checkRow(char myChar) {
        int wymiar = array.length;
        for (int wiersz = 0; wiersz < wymiar; wiersz++) {
            boolean wygrana = true;
            for (int kolumna = 0; kolumna < wymiar; kolumna++) {
                if (array[wiersz][kolumna] != myChar){
                    wygrana = false;
                    break;
                }
            }
            if (wygrana) return true;
        }
        return false;
    }

    public boolean checkCol(char myChar) {
        int wymiar = array.length;
        for (int kolumna = 0; kolumna < wymiar; kolumna++) {
            boolean wygrana = true;
            for (int wiersz = 0; wiersz < wymiar; wiersz++) {
                if (array[wiersz][kolumna] != myChar){
                    wygrana = false;
                    break;
                }
            }
            if (wygrana) return true;
        }
        return false;
    }

    public boolean checkSkos1(char myChar) {
        int wymiar = array.length;
        for (int i = 0; i < wymiar; i++) {
            if (array[i][i] != myChar) {
                return false;
            }
        }
        return true;
    }

    public boolean checkSkos2(char myChar) {
        int wymiar = array.length;
        int pom = 2;
        for (int i = 0; i < wymiar; i++) {
            if (array[i][pom] != myChar) {
                return false;
            }
            pom--;
        }
        return true;
    }

    public boolean endPlayer() {
        boolean end = false;
        if (checkRow(myChar) || checkCol(myChar) || checkSkos1(myChar) || checkSkos2(myChar)) {
            end = true;
        }
        return end;
    }

    public boolean endComp() {
        boolean end = false;
        if (checkRow(compChar) || checkCol(compChar) || checkSkos1(compChar) || checkSkos2(compChar)) {
            end = true;
        }
        return end;
    }

    public Label getStatus() {
        return status;
    }
}
