package com.axun.game.entity;

public class GameMessage {
    private String name;
    private Integer status;
    private Integer score;
    private Object grid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Object getGrid() {
        return grid;
    }

    public void setGrid(Object grid) {
        this.grid = grid;
    }
}
