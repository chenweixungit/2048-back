package com.axun.game.error;

public interface CommonError {
    public Integer getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}
