package com.axun.game.validator;



import org.apache.tomcat.util.buf.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {

    private boolean hasError = false;

    private Map<String,String> errorMsgMap = new HashMap<>();

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }
    public String getErrMsg(){
        Object[] objects = errorMsgMap.values().toArray();
        String[] strings = new String[objects.length];
        for(int i = 0;i < objects.length;++i){
            strings[i] = (String) objects[i];
        }
        return StringUtils.join(strings);
    }

}
