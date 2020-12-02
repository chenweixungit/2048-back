package com.axun.game.entity;


import java.util.List;
import java.util.Vector;

public class MessageReply {
    private Boolean isGame;
    private Vector<String> players;

    public Boolean getisGame() {
        return isGame;
    }

    public void setisGame(Boolean game) {
        isGame = game;
    }

    public Vector<String> getPlayers() {
        return players;
    }

    public void setPlayers(Vector<String> players) {
        this.players = players;
    }
}
