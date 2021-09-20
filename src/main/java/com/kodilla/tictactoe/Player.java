package com.kodilla.tictactoe;

public class Player {
    private String name;
    private int wonGames;

    public Player(String name, int wonGames) {
        this.name = name;
        this.wonGames = wonGames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWonGames() {
        return wonGames;
    }

    public void wonGameAdd() {
        wonGames++;
    }
}
