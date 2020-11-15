package com.axun.game.controller;


import com.axun.game.error.BussinessException;
import com.axun.game.error.EnumBusinessError;
import com.axun.game.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


public class BaseController {
    public static final String CONTENT_FORMAT_TYPE = "application/x-www-form-urlencoded";

    /**
     * 拦截异常的返回，定义一个正常的返回体
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object HandlerException(HttpServletRequest request, Exception ex){
        HashMap<Integer,String> map = new HashMap<>();
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setStatus("fail");
        if(ex instanceof BussinessException){
            BussinessException bussinessException = (BussinessException)ex;
            map.put(bussinessException.getErrCode(),bussinessException.getErrMsg());
            commonReturnType.setObject(map);
        }else{
            map.put(EnumBusinessError.UNKNOWN_EXCEPTION.getErrCode(),
                    EnumBusinessError.UNKNOWN_EXCEPTION.getErrMsg());
            commonReturnType.setObject(map);
        }
        return commonReturnType;
    }
}
