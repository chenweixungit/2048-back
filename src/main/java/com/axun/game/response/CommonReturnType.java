package com.axun.game.response;

public class CommonReturnType {
    private String status;
    private Object object;

    /**
     * 创建通用返回体
     * @param object
     * @return
     */
    public static CommonReturnType create(Object object){
        return create("success",object);
    }

    public static CommonReturnType create(String status,Object object){
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setStatus(status);
        commonReturnType.setObject(object);
        return commonReturnType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
