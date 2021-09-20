package com.kodilla.tictactoe;


import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Controller implements Serializable {

    public static final String PATH_TO_STATE = "/Users/szymonrosol/IdeaProjects/kodilla-tictactoe/temp.txt";
    public static final String PATH_TO_STATE1 = "/Users/szymonrosol/IdeaProjects/kodilla-tictactoe/Ranking.txt";
    private static final Random random = new Random();
    private static final Controller instance = new Controller();
    private List<MyButton> myButtons = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private char myChar = 0;
    private char compChar = 0;
    private Label status = new Label();
    private boolean gameOver = false;
    private boolean isHardOn = false;
    private Choose_O chooseO;
    private Choose_X chooseX;
    private SwitchButton switchButton;
    private Player player;

    private Controller() {
    }

    public static Controller getInstance() {
        return instance;
    }

    void addButton(MyButton button) {
        myButtons.add(button);
    }

    void addChoose_X(Choose_X chooseX) {
        this.chooseX = chooseX;
    }

    void addChoose_O(Choose_O chooseO) {
        this.chooseO = chooseO;
    }

    void addSwitchButton(SwitchButton switchButton) {
        this.switchButton = switchButton;
    }

    public void createPlayer(String playerName) {
        player = new Player(playerName, 0);
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

    public int isFieldFree() {
        int numberOfFreeFields = myButtons.stream()
                .filter(button -> button.getMyChar() == 0)
                .collect(Collectors.toList()).size();
        return numberOfFreeFields;
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
        if (isFieldFree() != 9) {
            status.setText("End the current game");
        } else {
            myChar = 'X';
            compChar = 'O';
            setColor();
        }
    }

    public void setOChar(Choose_O chooseO) {
        if (isFieldFree() != 9) {
            status.setText("End the current game");
        } else {
            myChar = 'O';
            compChar = 'X';
            setColor();
        }
    }

    public void setDifficultyLevel() {
        if (isFieldFree() != 9) {
            status.setText("End the current game");
        } else {
            if (isHardOn) {
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
        myButtons.stream()
                .forEach(el -> el.resetChar());
        myButtons.stream()
                .forEach(el -> el.setFill(Color.web("#ADB3BC")));
        status.setText("New game, select a character");
    }

    private void checkIfComputerWon() {
        if (endComp()) {
            status.setText("Computer won!");
            gameOver = true;
        } else if (isFieldFree() == 0 && !endComp() && !endPlayer()) {
            status.setText("TIE!");
            gameOver = true;
        } else {
            status.setText("Your turn...");
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
                    .filter(button -> button.getMyChar() == 0)
                    .collect(Collectors.toList());
            MyButton button = emptyButtons.get(random.nextInt(emptyButtons.size()));
            button.setFill(new ImagePattern(changeImage(compChar)));
            button.setChar(compChar);
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
                .filter(button -> button.getMyChar() == 0)
                .collect(Collectors.toList());
        return emptyButtons.get(random.nextInt(emptyButtons.size()));
    }

    private MyButton checkColByCompPlayer(int col) {
        List<MyButton> colSecond = Arrays.asList(getButton(col, 0), getButton(col, 1), getButton(col, 2));

        long numberOfPlayerButtons = colSecond.stream()
                .filter(button -> button.getMyChar() != 0)
                .filter(button -> myChar == button.getMyChar()).count();
        long numberOfEmptyButtons = colSecond.stream().filter(button -> button.getMyChar() == 0).count();

        if (numberOfPlayerButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    colSecond.stream().filter(button -> button.getMyChar() == 0).findFirst().orElseThrow();
            return chosenButton;
        }
        return null;
    }

    private MyButton checkRowByCompPlayer(int row) {
        List<MyButton> rowFirst = Arrays.asList(getButton(0, row), getButton(1, row), getButton(2, row));

        long numberOfPlayerButtons = rowFirst.stream()
                .filter(button -> button.getMyChar() != 0)
                .filter(button -> myChar == button.getMyChar()).count();
        long numberOfEmptyButtons = rowFirst.stream().filter(button -> button.getMyChar() == 0).count();

        if (numberOfPlayerButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    rowFirst.stream().filter(button -> button.getMyChar() == 0).findFirst().orElseThrow();
            return chosenButton;
        }
        return null;
    }


    private MyButton checkRake1ByCompPlayer() {
        List<MyButton> Rake1 = Arrays.asList(getButton(0, 0), getButton(1, 1), getButton(2, 2));

        long numberOfPlayerButtons = Rake1.stream()
                .filter(button -> button.getMyChar() != 0)
                .filter(button -> myChar == button.getMyChar()).count();
        long numberOfEmptyButtons = Rake1.stream().filter(button -> button.getMyChar() == 0).count();

        if (numberOfPlayerButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    Rake1.stream().filter(button -> button.getMyChar() == 0).findFirst().orElseThrow();
            return chosenButton;
        }
        return null;
    }

    private MyButton checkRake2ByCompPlayer() {
        List<MyButton> Rake2 = Arrays.asList(getButton(2, 0), getButton(1, 1), getButton(0, 2));

        long numberOfPlayerButtons = Rake2.stream()
                .filter(button -> button.getMyChar() != 0)
                .filter(button -> myChar == button.getMyChar()).count();
        long numberOfEmptyButtons = Rake2.stream().filter(button -> button.getMyChar() == 0).count();

        if (numberOfPlayerButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    Rake2.stream().filter(button -> button.getMyChar() == 0).findFirst().orElseThrow();
            return chosenButton;
        }
        return null;
    }

    private MyButton checkColByComp(int col) {
        List<MyButton> colSecond = Arrays.asList(getButton(col, 0), getButton(col, 1), getButton(col, 2));

        long numberOfCompButtons = colSecond.stream()
                .filter(button -> button.getMyChar() != 0)
                .filter(button -> compChar == button.getMyChar()).count();
        long numberOfEmptyButtons = colSecond.stream().filter(button -> button.getMyChar() == 0).count();

        if (numberOfCompButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    colSecond.stream().filter(button -> button.getMyChar() == 0).findFirst().orElseThrow();
            return chosenButton;
        }
        return null;
    }

    private MyButton checkRowByComp(int row) {
        List<MyButton> rowFirst = Arrays.asList(getButton(0, row), getButton(1, row), getButton(2, row));

        long numberOfCompButtons = rowFirst.stream()
                .filter(button -> button.getMyChar() != 0)
                .filter(button -> compChar == button.getMyChar()).count();
        long numberOfEmptyButtons = rowFirst.stream().filter(button -> button.getMyChar() == 0).count();

        if (numberOfCompButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    rowFirst.stream().filter(button -> button.getMyChar() == 0).findFirst().orElseThrow();
            return chosenButton;
        }
        return null;
    }

    private MyButton checkRake1ByComp() {
        List<MyButton> Rake1 = Arrays.asList(getButton(0, 0), getButton(1, 1), getButton(2, 2));

        long numberOfCompButtons = Rake1.stream()
                .filter(button -> button.getMyChar() != 0)
                .filter(button -> compChar == button.getMyChar()).count();
        long numberOfEmptyButtons = Rake1.stream().filter(button -> button.getMyChar() == 0).count();

        if (numberOfCompButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    Rake1.stream().filter(button -> button.getMyChar() == 0).findFirst().orElseThrow();
            return chosenButton;
        }
        return null;
    }

    private MyButton checkRake2ByComp() {
        List<MyButton> Rake2 = Arrays.asList(getButton(2, 0), getButton(1, 1), getButton(0, 2));

        long numberOfCompButtons = Rake2.stream()
                .filter(button -> button.getMyChar() != 0)
                .filter(button -> compChar == button.getMyChar()).count();
        long numberOfEmptyButtons = Rake2.stream().filter(button -> button.getMyChar() == 0).count();

        if (numberOfCompButtons == 2 && numberOfEmptyButtons == 1) {
            MyButton chosenButton =
                    Rake2.stream().filter(button -> button.getMyChar() == 0).findFirst().orElseThrow();
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

            button.setFill(new ImagePattern(changeImage(compChar)));
            button.setChar(compChar);
        }
    }

    private void checkIfPlayerWon() {
        if (endPlayer()) {
            status.setText("You won!");
            gameOver = true;
            player.wonGameAdd();
        } else if (isFieldFree() == 0 && !endComp() && !endPlayer()) {
            status.setText("TIE!");
            gameOver = true;
        } else {
            status.setText("Computer turn...");
        }
    }

    private void makePlayerMove(MyButton button) {
        if (!gameOver) {
            button.setFill(new ImagePattern(changeImage(myChar)));
            button.setChar(myChar);
        }
    }

    private boolean validateMove(MyButton button) {
        if (button.getMyChar() != 0 && !gameOver) {
            status.setText("Wrong move!");
            return false;
        } else if (myChar == 0 || compChar == 0) {
            status.setText("No character selected");
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

    public MyButton findButton(int row, int col) {
        for (MyButton button : myButtons) {
            if ((button.getRow() == row) && (button.getCol() == col)) {
                return button;
            }
        }
        return null;
    }

    public boolean checkRow(char myChar) {
        for (int row = 0; row < 3; row++) {
            boolean win = true;
            for (int col = 0; col < 3; col++) {
                MyButton button = findButton(row, col);
                if (!button.getMyChar().equals(myChar)) {
                    win = false;
                    break;
                }
            }
            if (win)
                return true;
        }
        return false;
    }

    public boolean checkCol(char myChar) {
        for (int col = 0; col < 3; col++) {
            boolean win = true;
            for (int row = 0; row < 3; row++) {
                MyButton button = findButton(row, col);
                if (button.getMyChar() != myChar) {
                    win = false;
                    break;
                }
            }
            if (win)
                return true;
        }
        return false;
    }

    public boolean checkSkos1(char myChar) {
        for (int i = 0; i < 3; i++) {
            MyButton button = findButton(i, i);
            if (button.getMyChar() != myChar) {
                return false;
            }
        }
        return true;
    }

    public boolean checkSkos2(char myChar) {
        int auxiliary = 2;
        for (int i = 0; i < 3; i++) {
            MyButton button = findButton(i, auxiliary);
            if (button.getMyChar() != myChar) {
                return false;
            }
            auxiliary--;
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

    public void save() {
        status.setText("Save clicked");

        String content = "";
        for (MyButton myButton : myButtons) {
            if (myButton.getMyChar() == 0) {
                content += "0";
            } else if (myButton.getMyChar() == 'X') {
                content += "1";
            } else {
                content += "2";
            }
        }

        if (myChar == 'X') {
            content += "5";
        } else if (myChar == 'O') {
            content += "6";
        }

        try {
            Files.write(Path.of(PATH_TO_STATE),
                    content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        status.setText("Load clicked");

        try {
            String content = new String(Files.readAllBytes(Paths.get(PATH_TO_STATE)));

            for (int col = 0; col < 3; col++) {
                for (int row = 0; row < 3; row++) {
                    MyButton myButton = myButtons.get(col * 3 + row);
                    String element = String.valueOf(content.charAt(col * 3 + row));
                    if (element.equals("1")) {
                        myButton.setChar('X');
                        myButton.setFill(new ImagePattern(changeImage('X')));
                    } else if (element.equals("2")) {
                        myButton.setChar('O');
                        myButton.setFill(new ImagePattern(changeImage('O')));
                    }
                    element = String.valueOf(content.charAt(9));
                    if (element.equals("5")) {
                        myChar = 'X';
                        compChar = 'O';
                    } else if (element.equals("6")) {
                        myChar = 'O';
                        compChar = 'X';
                    }
                    setColor();

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveRankList() {

        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(PATH_TO_STATE1)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        content += (player.getName() + "," + player.getWonGames() + "\n");


        try {
            Files.write(Path.of(PATH_TO_STATE1),
                    content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRankList() {

        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader("Ranking.txt"));
            String line = bufferedReader.readLine();
            while (line != null) {
                int index = line.indexOf(',');
                String name = line.substring(0, index);
                int wonGames = Integer.parseInt(line.substring(index + 1));
                players.add(new Player(name, wonGames));
                line = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
