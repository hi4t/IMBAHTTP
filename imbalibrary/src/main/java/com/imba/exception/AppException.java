package com.imba.exception;

/**
 * Created by zace on 2016/4/28.
 */
public class AppException extends Exception {

    private int status;
    private String errMsg;

    public AppException(int status, String errMsg) {
        super(errMsg);
        this.status = status;
        this.errMsg = errMsg;
    }

    public AppException(String detailMessage) {
        super(detailMessage);
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
