package com.imba.imbahttp;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by zace on 2015/4/26.
 */
public interface ICallBack<T> {

    void onSuccess(T result);

    void onFailure(Exception e);

    T parsr(HttpURLConnection connection, OnProgressUpdateListener listener) throws IOException, Exception;

    void onProgressUpdate(long currentLen, long totalLen);
}
