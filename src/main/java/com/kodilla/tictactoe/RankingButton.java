package com.kodilla.tictactoe;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
        grid.setVgap(150);
        grid.setHgap(50);

        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 10; row++) {
                MyLabel myLabel = new MyLabel(col,row);
                myLabel.setText("aaaaa");
                grid.add(myLabel, col, row, 1, 1);
            }
        }

        Button exit = new Button("EXIT");
        GridPane.setConstraints(exit, 10, 0);
        grid.getChildren().add(exit);
        Scene scene = new Scene(grid, 750, 750, Color.BLUE);
        Stage stage = new Stage();
        stage.setTitle("RANKING");
        stage.setScene(scene);
        stage.show();
    }
}
