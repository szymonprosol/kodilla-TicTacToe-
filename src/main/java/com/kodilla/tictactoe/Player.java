package com.kodilla.tictactoe;

public class Player {
    private String name;
    private int wonGames;

    public Player(String name) {
        this.name = name;
        wonGames = 0;
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

    public void setWonGames(int wonGames) {
        this.wonGames = wonGames;
    }
}
