package com.axun.game.error;

public enum EnumBusinessError implements CommonError{
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UER_NOT_EXIST(10002,"用户不存在"),
    UNKNOWN_EXCEPTION(10003,"未知异常"),
    FAIL_LOGIN(10004,"用户不存在或密码错误")
    ;

    private Integer errCode;
    private String errMsg;
    private EnumBusinessError(Integer errCode,String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public Integer getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
