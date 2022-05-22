package com.lhrlyn.cn.lhrlynadmin.user.util.response;

public class BaseResponse {
    public static final int SERVER_SUCCESS_STATUS = 200;
    public static final int SERVER_FAILED_STATUS = 500;
    protected int status = 200;
    protected String message;
    protected boolean success = true;
    protected String stacktrace;

    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseResponse(int status, String message, boolean isSuccess) {
        this.status = status;
        this.message = message;
        this.success = isSuccess;
    }

    public BaseResponse(int status, String message, String stacktrace) {
        this.status = status;
        this.message = message;
        this.stacktrace = stacktrace;
    }

    public BaseResponse() {
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStacktrace() {
        return this.stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }
}
