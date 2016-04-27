package com.imba.imbahttp;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by zace on 2016/4/26.
 */
public interface ICallBack<T> {

    void onSuccess(T result);

    void onFailure(Exception e);

    T parsr(HttpURLConnection connection) throws IOException, Exception;
}
