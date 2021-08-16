package com.kodilla.tictactoe;


import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Controller implements Serializable {

    private static final Random random = new Random();
    private static final Controller instance = new Controller();
    private List<MyButton> myButtons = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private char myChar = 0;
    private char compChar = 0;
    private char[][] array = new char[3][3];
    private int numberOfMoves = 0;
    private Label status = new Label();
    private boolean gameOver = false;
    private boolean isHardOn = true;
    private Choose_O chooseO;
    private Choose_X chooseX;
    private SwitchButton switchButton;

    private Controller() {
    }

    public static Controller getInstance() {
        return instance;
    }

    void addButton(MyButton button) {
        myButtons.add(button);
    }
    void addChoose_X(Choose_X chooseX) {this.chooseX = chooseX;}
    void addChoose_O(Choose_O chooseO) {this.chooseO = chooseO;}
    void addSwitchButton(SwitchButton switchButton) {this.switchButton = switchButton;}

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

    public void setColor() {
        if (myChar == 'X') {
            chooseX.setStyle("-fx-border-color: #4BFAC0;" + "-fx-border-width: 3;" + "-fx-border-insets: 5;");
            chooseO.setStyle("-fx-border-color:transparent");
        } else if (myChar == 'O') {
            chooseO.setStyle("-fx-border-color: #4BFAC0;" + "-fx-border-width: 3;" + "-fx-border-insets: 5;");
            chooseX.setStyle("-fx-border-color:transparent");
        } else {
            chooseX.setStyle("-fx-border-color:transparent");
            chooseO.setStyle("-fx-border-color:transparent");
        }
    }

    public void setXChar(Choose_X chooseX) {
        if (numberOfMoves != 0) {
            status.setText("Zakończ obecną rozgrywkę");
        } else {
            myChar = 'X';
            compChar = 'O';
            setColor();
        }
    }

    public void setOChar(Choose_O chooseO) {
        if (numberOfMoves != 0) {
            status.setText("Zakończ obecną rozgrywkę");
        } else {
            myChar = 'O';
            compChar = 'X';
            setColor();
        }
    }

    public void setDifficultyLevel() {
        if (numberOfMoves != 0) {
            status.setText("Zakończ obecną rozgrywkę");
        } else {
            if (isHardOn){
                isHardOn = false;
                switchButton.setStyle("-fx-border-color:transparent");
            } else {
                isHardOn = true;
                switchButton.setStyle("-fx-border-color: #4BFAC0;" + "-fx-border-width: 3;" + "-fx-border-insets: 5;");
            }
        }
    }

    public void newGame(NewGameButton nGmButton) {
        gameOver = false;
        myChar = 0;
        compChar = 0;
        setColor();
        array = new char[3][3];
        numberOfMoves = 0;
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
        } else if (numberOfMoves == 9) {
            status.setText("Brak możliwości ruchu - REMIS!");
            gameOver = true;
        } else {
            status.setText("Twój ruch!");
        }
    }

    private void makeComputerMove() {
        if (!isHardOn) {
            makeSimpleCompMove();
        } else {
            makeComplexCompMove();
        }
    }

    private void makeSimpleCompMove() {
        if (!gameOver) {
            List<MyButton> emptyButtons = myButtons.stream()
                    .filter(button -> button.getMyChar() == null)
                    .collect(Collectors.toList());
            MyButton button = emptyButtons.get(random.nextInt(emptyButtons.size()));
            array[button.getCol()][button.getRow()] = compChar;
            button.setFill(new ImagePattern(changeImage(compChar)));
            button.setChar(compChar);
            numberOfMoves++;
        }
    }

    private MyButton chooseCompButton() {

        // 1 check if comp can win
        // row
        MyButton chosenButton;
        chosenButton = checkRowByComp(0);
        if (chosenButton != null) {
            return chosenButton;
        }
        chosenButton = checkRowByComp(1);
        if (chosenButton != null) {
            return chosenButton;
        }
        chosenButton = checkRowByComp(2);
        if (chosenButton != null) {
            return chosenButton;
        }

        // col
        chosenButton = checkColByComp(0);
        if (chosenButton != null) {
            return chosenButton;
        }
        chosenButton = checkColByComp(1);
        if (chosenButton != null) {
            return chosenButton;
        }
        chosenButton = checkColByComp(2);
        if (chosenButton != null) {
            return chosenButton;
        }

        // skos 1
        chosenButton = checkRake1ByComp();
        if (chosenButton != null) {
            return chosenButton;
        }

        // skos 2
        chosenButton = checkRake2ByComp();
        if (chosenButton != null) {
            return chosenButton;
        }

        // 2 check if player can win
        // row
        chosenButton = checkRowByCompPlayer(0);
        if (chosenButton != null) {
            return chosenButton;
        }
        chosenButton = checkRowByCompPlayer(1);
        if (chosenButton != null) {
            return chosenButton;
        }
        chosenButton = checkRowByCompPlayer(2);
        if (chosenButton != null) {
            return chosenButton;
        }

        //col
        chosenButton = checkColByCompPlayer(0);
        if (chosenButton != null) {
            return chosenButton;
        }
        chosenButton = checkColByCompPlayer(1);
        if (chosenButton != null) {
            return chosenButton;
        }
        chosenButton = checkColByCompPlayer(2);
        if (chosenButton != null) {
            return chosenButton;
        }
        // skos 1
        chosenButton = checkRake1ByCompPlayer();
        if (chosenButton != null) {
            return chosenButton;
        }

        // skos 2
        chosenButton = checkRake2ByCompPlayer();
        if (chosenButton != null) {
            return chosenButton;
        }

        List<MyButton> emptyButtons = myButtons.stream()
                .filter(button -> button.getMyChar() == null)
                .collect(Collectors.toList());
        return emptyButtons.get(random.nextInt(emptyButtons.size()));
    }

    private MyButton checkColByCompPlayer(int col) {
        List<MyButton> colSecond = Arrays.asList(getButton(col, 0), getButton(col, 1), getButton(col, 2));

        long numberOfPlayerButtons = colSecond.stream()
                .filter(button -> button.getMyChar() != null)
                .filter(button -> myChar == button.getMyChar()).count();
        long numberOfEmptyButtons = colSecond.stream().filter(button -> button.getMyChar() == null).count();

        if (numberOfPlayerButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    colSecond.stream().filter(button -> button.getMyChar() == null).findFirst().orElseThrow();
            return chosenButton;
        }
        return null;
    }

    private MyButton checkRowByCompPlayer(int row) {
        List<MyButton> rowFirst = Arrays.asList(getButton(0, row), getButton(1, row), getButton(2, row));

        long numberOfPlayerButtons = rowFirst.stream()
                .filter(button -> button.getMyChar() != null)
                .filter(button -> myChar == button.getMyChar()).count();
        long numberOfEmptyButtons = rowFirst.stream().filter(button -> button.getMyChar() == null).count();

        if (numberOfPlayerButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    rowFirst.stream().filter(button -> button.getMyChar() == null).findFirst().orElseThrow();
            return chosenButton;
        }
        return null;
    }


    private MyButton checkRake1ByCompPlayer() {
        List<MyButton> Rake1 = Arrays.asList(getButton(0, 0), getButton(1, 1), getButton(2, 2));

        long numberOfPlayerButtons = Rake1.stream()
                .filter(button -> button.getMyChar() != null)
                .filter(button -> myChar == button.getMyChar()).count();
        long numberOfEmptyButtons = Rake1.stream().filter(button -> button.getMyChar() == null).count();

        if (numberOfPlayerButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    Rake1.stream().filter(button -> button.getMyChar() == null).findFirst().orElseThrow();
            return chosenButton;
        }
        return null;
    }

    private MyButton checkRake2ByCompPlayer() {
        List<MyButton> Rake2 = Arrays.asList(getButton(2, 0), getButton(1, 1), getButton(0, 2));

        long numberOfPlayerButtons = Rake2.stream()
                .filter(button -> button.getMyChar() != null)
                .filter(button -> myChar == button.getMyChar()).count();
        long numberOfEmptyButtons = Rake2.stream().filter(button -> button.getMyChar() == null).count();

        if (numberOfPlayerButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    Rake2.stream().filter(button -> button.getMyChar() == null).findFirst().orElseThrow();
            return chosenButton;
        }
        return null;
    }

    private MyButton checkColByComp(int col) {
        List<MyButton> colSecond = Arrays.asList(getButton(col, 0), getButton(col, 1), getButton(col, 2));

        long numberOfCompButtons = colSecond.stream()
                .filter(button -> button.getMyChar() != null)
                .filter(button -> compChar == button.getMyChar()).count();
        long numberOfEmptyButtons = colSecond.stream().filter(button -> button.getMyChar() == null).count();

        if (numberOfCompButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    colSecond.stream().filter(button -> button.getMyChar() == null).findFirst().orElseThrow();
            return chosenButton;
        }
        return null;
    }

    private MyButton checkRowByComp(int row) {
        List<MyButton> rowFirst = Arrays.asList(getButton(0, row), getButton(1, row), getButton(2, row));

        long numberOfCompButtons = rowFirst.stream()
                .filter(button -> button.getMyChar() != null)
                .filter(button -> compChar == button.getMyChar()).count();
        long numberOfEmptyButtons = rowFirst.stream().filter(button -> button.getMyChar() == null).count();

        if (numberOfCompButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    rowFirst.stream().filter(button -> button.getMyChar() == null).findFirst().orElseThrow();
            return chosenButton;
        }
        return null;
    }

    private MyButton checkRake1ByComp() {
        List<MyButton> Rake1 = Arrays.asList(getButton(0, 0), getButton(1, 1), getButton(2, 2));

        long numberOfCompButtons = Rake1.stream()
                .filter(button -> button.getMyChar() != null)
                .filter(button -> compChar == button.getMyChar()).count();
        long numberOfEmptyButtons = Rake1.stream().filter(button -> button.getMyChar() == null).count();

        if (numberOfCompButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    Rake1.stream().filter(button -> button.getMyChar() == null).findFirst().orElseThrow();
            return chosenButton;
        }
        return null;
    }

    private MyButton checkRake2ByComp() {
        List<MyButton> Rake2 = Arrays.asList(getButton(2, 0), getButton(1, 1), getButton(0, 2));

        long numberOfCompButtons = Rake2.stream()
                .filter(button -> button.getMyChar() != null)
                .filter(button -> compChar == button.getMyChar()).count();
        long numberOfEmptyButtons = Rake2.stream().filter(button -> button.getMyChar() == null).count();

        if (numberOfCompButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    Rake2.stream().filter(button -> button.getMyChar() == null).findFirst().orElseThrow();
            return chosenButton;
        }
        return null;
    }

    private MyButton getButton(int col, int row) {
        return myButtons.stream()
                .filter(button -> button.getCol() == col)
                .filter(button -> button.getRow() == row)
                .findFirst().orElseThrow();
    }

    private void makeComplexCompMove() {
        if (!gameOver) {

            MyButton button = chooseCompButton();

            array[button.getCol()][button.getRow()] = compChar;
            button.setFill(new ImagePattern(changeImage(compChar)));
            button.setChar(compChar);
            numberOfMoves++;
        }
    }

    private void checkIfPlayerWon() {
        if (endPlayer()) {
            status.setText("GRATULACJE ZWYCIĘŻYŁEŚ!");
            gameOver = true;
        } else if (numberOfMoves == 9) {
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
            numberOfMoves++;
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
                if (array[wiersz][kolumna] != myChar) {
                    wygrana = false;
                    break;
                }
            }
            if (wygrana)
                return true;
        }
        return false;
    }

    public boolean checkCol(char myChar) {
        int wymiar = array.length;
        for (int kolumna = 0; kolumna < wymiar; kolumna++) {
            boolean wygrana = true;
            for (int wiersz = 0; wiersz < wymiar; wiersz++) {
                if (array[wiersz][kolumna] != myChar) {
                    wygrana = false;
                    break;
                }
            }
            if (wygrana)
                return true;
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

    public List<Player> getPlayers() {
        return players;
    }
    public void addPlayers(Player player) {
        players.add(player);
    }
}
