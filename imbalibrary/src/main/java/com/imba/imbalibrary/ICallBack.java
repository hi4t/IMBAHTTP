package com.imba.imbalibrary;

import com.imba.exception.AppException;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by zace on 2015/4/26.
 */
public interface ICallBack<T> {

    void onSuccess(T result);

    void onFailure(AppException e);

    T parsr(HttpURLConnection connection, OnProgressUpdateListener listener) throws AppException;

    void onProgressUpdate(long currentLen, long totalLen);

    void cancel(boolean b);

    /**
     * before onSuccess
     *
     * @param t
     * @return
     */
    T postResult(T t);

    T preRequest();
}
