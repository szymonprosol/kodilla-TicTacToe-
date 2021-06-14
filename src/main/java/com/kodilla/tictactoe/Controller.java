package com.kodilla.tictactoe;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static final Controller instance = new Controller();
    private List<MyButton> myButtons = new ArrayList<>();
    private char myChar = 'X';
    private char[][] tablica = new char[3][3];
    private int liczbaRuchow = 0;

    private Controller() {
    }

    public static Controller getInstance() {
        return instance;
    }

    void addButton(MyButton button) {
        myButtons.add(button);
    }

    public void click(MyButton button) {
        if (tablica[button.getCol()][button.getRow()] == 0) {
            tablica[button.getCol()][button.getRow()] = myChar;
            button.setFill(new ImagePattern(changeImage(myChar)));
            myChar = myChar == 'X' ? 'O' : 'X';
            liczbaRuchow++;
        } else {
            System.out.println("Ruch niepoprawny!");
        }
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

    public static boolean checkRow(char[][] tablica, char myChar) {
        int wymiar = tablica.length;
        for (int wiersz = 0; wiersz < wymiar; wiersz++) {
            boolean wygrana = true;
            for (int kolumna = 0; kolumna < wymiar; kolumna++) {
                if (tablica[wiersz][kolumna] != myChar){
                    wygrana = false;
                    break;
                }
            }
            if (wygrana) return true;
        }
        return false;
    }

    public static boolean checkCol(char[][] tablica, char myChar) {
        int wymiar = tablica.length;
        for (int kolumna = 0; kolumna < wymiar; kolumna++) {
            boolean wygrana = true;
            for (int wiersz = 0; wiersz < wymiar; wiersz++) {
                if (tablica[wiersz][kolumna] != myChar){
                    wygrana = false;
                    break;
                }
            }
            if (wygrana) return true;
        }
        return false;
    }
    public static boolean checkSkos1(char[][]tablica, char myChar) {
        int wymiar = tablica.length;
        for (int i = 0; i < wymiar; i++) {
            if (tablica[i][i] != myChar) {
                return false;
            }
        }
        return true;
    }

    public boolean end() {
        boolean end = false;
        if (checkRow(tablica,myChar) || checkCol(tablica,myChar) || checkSkos1(tablica,myChar)) {
            System.out.println("Wygrał " + myChar);
            end = true;
        } else if (Controller.getInstance().getLiczbaRuchow() == 9) {
            System.out.println("Brak możliwości ruchu! Remis!");
            end = true;
        }
        return end;
    }

    public char getMyChar() {
        return myChar;
    }

    public char[][] getTablica() {
        return tablica;
    }

    public int getLiczbaRuchow() {
        return liczbaRuchow;
    }
}
