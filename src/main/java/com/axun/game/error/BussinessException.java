package com.axun.game.error;

public class BussinessException extends Exception implements CommonError{
    private CommonError commonError;

    // 直接接收errorMessage构造Exception
    public BussinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }

    // 接收自定义errorMessage构造Exception
    public BussinessException(CommonError commonError,String errMsg){
        super();
        this.commonError = commonError;
        commonError.setErrMsg(errMsg);
    }

    @Override
    public Integer getErrCode() {
        return commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        commonError.setErrMsg(errMsg);
        return this;
    }
}
