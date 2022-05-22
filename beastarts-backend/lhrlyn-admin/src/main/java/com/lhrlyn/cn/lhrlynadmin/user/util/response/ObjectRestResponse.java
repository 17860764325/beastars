package com.lhrlyn.cn.lhrlynadmin.user.util.response;

import java.util.MissingFormatArgumentException;

public class ObjectRestResponse<T> extends BaseResponse {
    private T data;

    public ObjectRestResponse() {
    }

    public static <T> ObjectRestResponse success() {
        return new ObjectRestResponse();
    }

    public static <T> ObjectRestResponse success(T data) {
        return (new ObjectRestResponse()).data(data);
    }

    public static <T> ObjectRestResponse failed() {
        return (new ObjectRestResponse()).status(500);
    }

    public static <T> ObjectRestResponse failed(String message) {
        ObjectRestResponse<T> response = new ObjectRestResponse();
        return response.status(500).message(message);
    }

    public ObjectRestResponse message(String message) {
        this.message = message;
        return this;
    }

    public ObjectRestResponse message(String message, Object... objs) {
        try {
            this.message = String.format(message.replaceAll("\\{\\}", "%s"), objs);
            return this;
        } catch (MissingFormatArgumentException var4) {
            throw new RuntimeException("设置message失败，占位符与参数个数不匹配");
        }
    }

    public ObjectRestResponse status(int status) {
        this.status = status;
        return this;
    }

    public ObjectRestResponse data(T data) {
        this.setData(data);
        return this;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
