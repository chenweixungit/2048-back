package com.axun.game.entity;

public class PushMessage {

    // 登录用户编号
    private Integer loginUserNum;

    // 推送内容
    private String content;

    public PushMessage() {
    }

    public PushMessage(Integer loginUserNum, String content) {
        this.loginUserNum = loginUserNum;
        this.content = content;
    }

    public Integer getLoginUserNum() {
        return loginUserNum;
    }

    public void setLoginUserNum(Integer loginUserNum) {
        this.loginUserNum = loginUserNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
