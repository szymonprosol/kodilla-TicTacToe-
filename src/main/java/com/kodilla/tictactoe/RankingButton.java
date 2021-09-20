package com.kodilla.tictactoe;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RankingButton extends Button {

    public RankingButton() {
        setTextFill(Color.web("#000000"));
        setStyle("-fx-border-color:transparent");
        setFont(new Font("Arial", 20));
        minHeight(5);
        minWidth(5);
        setText("Ranking");
        setOnMouseClicked(event -> ranking());
    }

    public void ranking() {

        // popup
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(30);
        grid.setHgap(50);

        MyLabel numer = new MyLabel(0,0);
        numer.setText("Number");
        grid.add(numer, 0, 0, 1, 1);

        MyLabel name = new MyLabel(1,0);
        name.setText("Name");
        grid.add(name, 1, 0, 1, 1);

        MyLabel wonGames = new MyLabel(2,0);
        wonGames.setText("Won Games");
        grid.add(wonGames, 2, 0, 1, 1);

        for (int row = 1; row < 10; row++) {
            MyLabel myLabel = new MyLabel(0,row);
            myLabel.setText(String.valueOf(row));
            grid.add(myLabel, 0, row, 1, 1);
        }

        List<Player> segregatePlayers = Controller.getInstance().getPlayers()
                .stream().sorted(Comparator.comparingInt(Player::getWonGames)
                .reversed())
                .collect(Collectors.toList());

        for (int row = 1; row < segregatePlayers.size()+1; row++) {
            MyLabel myLabel = new MyLabel(1,row);
            myLabel.setText(segregatePlayers.get(row-1).getName());
            grid.add(myLabel, 1, row, 1, 1);
        }

        for (int row = 1; row < segregatePlayers.size()+1; row++) {
            MyLabel myLabel = new MyLabel(2,row);
            myLabel.setText(String.valueOf(segregatePlayers.get(row-1).getWonGames()));
            grid.add(myLabel, 2, row, 1, 1);
        }

        Scene scene = new Scene(grid, 750, 750, Color.BLUE);
        Stage stage = new Stage();
        stage.setTitle("RANKING");
        stage.setScene(scene);
        stage.show();
    }
}
