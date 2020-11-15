package com.axun.game.dataObjects;

public class UserPasswordDO {
    private Integer id;

    private Integer userId;

    private String encryedPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEncryedPassword() {
        return encryedPassword;
    }

    public void setEncryedPassword(String encryedPassword) {
        this.encryedPassword = encryedPassword;
    }
}