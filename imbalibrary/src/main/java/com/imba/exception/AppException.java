package com.imba.exception;

/**
 * Created by zace on 2016/4/28.
 */
public class AppException extends Exception {

    private int status;
    private String errMsg;

    public enum ErrorType {CANCEL,TIMEOUT, SERVER, IO, JSON, FILENOTFOUND,NET}

    private ErrorType type;

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }

    public AppException(ErrorType type, int status, String errMsg) {
        super(errMsg);
        this.type = type;
        this.status = status;
        this.errMsg = errMsg;
    }

    public AppException(ErrorType type, String detailMessage) {
        super(detailMessage);
        this.type = type;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
