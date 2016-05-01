package com.imba.imbalibrary;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Created by zace on 2015/4/26.
 */
public class Request {

    private String url;
    private Map<String, String> header;
    private String content;
    private int maxRetryCount = 5;
    private ICallBack callBack;
    private OnGlobleExceptionListener listener;
    private boolean isCancel;
    private String tag;
    private RequestTask task;
    private OnProgressUploadListener uploadListener;

    public enum RequestMethod {GET, POST, DELETE, PUT}

    private RequestMethod method;

    private String filePath;
    private ArrayList<FileEntity> entities;

    public OnProgressUploadListener getUploadListener() {
        return uploadListener;
    }

    public void setUploadListener(OnProgressUploadListener uploadListener) {
        this.uploadListener = uploadListener;
    }

    public ArrayList<FileEntity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<FileEntity> entities) {
        this.entities = entities;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getMaxRetryCount() {
        return maxRetryCount;
    }

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    public OnGlobleExceptionListener getListener() {
        return listener;
    }

    public void setOnGlobleExceptionListener(OnGlobleExceptionListener listener) {
        this.listener = listener;
    }

    protected void cancel(boolean force) {
        this.isCancel = true;
        this.callBack.cancel(true);
        if (force && task != null) {
            task.cancel(force);
        }
    }

    public boolean checkIsCanceled() {
        return isCancel;
    }

    public String getTag() {
        return this.tag;
    }

    public void excute(Executor mExcutor) {
        task = new RequestTask(this);
        task.executeOnExecutor(mExcutor);
    }

    public ICallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(ICallBack callBack) {
        this.callBack = callBack;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public Request(String url) {
        this.url = url;
        this.method = RequestMethod.GET;
    }

    public Request(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
