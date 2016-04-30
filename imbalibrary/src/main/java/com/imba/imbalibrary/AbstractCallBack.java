package com.imba.imbalibrary;

import android.text.TextUtils;

import com.imba.exception.AppException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by zace on 2015/4/26.
 */
public abstract class AbstractCallBack<T> implements ICallBack<T> {

    private String path;
    public boolean isCancel;

    @Override
    public T parsr(HttpURLConnection conn, OnProgressUpdateListener listener) throws AppException {
        try {
            if (checkIsCancel()) {
                throw new AppException(AppException.ErrorType.CANCEL, "the request has been cancel");
            }

            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {

                if (!TextUtils.isEmpty(path)) {
                    File file = new File(path);
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                    FileOutputStream out = new FileOutputStream(path);
                    InputStream is = conn.getInputStream();
                    byte[] buffer = new byte[2048];

                    int len;
                    int currentLen = 0;
                    int totalLen = conn.getContentLength();

                    while ((len = is.read(buffer)) != -1) {
                        if (checkIsCancel()) {
                            throw new AppException(AppException.ErrorType.CANCEL, "the request has been cancel");
                        }
                        out.write(buffer, 0, len);
                        currentLen += len;
                        if (listener != null) {
                            listener.onProgressUpdaet(currentLen, totalLen);
                        }

                    }
                    is.close();
                    out.flush();
                    out.close();
                    T t = bindData(path);

                    return postResult(t);
                } else {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    InputStream is = conn.getInputStream();
                    byte[] buffer = new byte[2048];

                    int len;

                    while ((len = is.read(buffer)) != -1) {
                        if (checkIsCancel()) {
                            throw new AppException(AppException.ErrorType.CANCEL, "the request has been cancel");
                        }
                        out.write(buffer, 0, len);
                    }
                    is.close();
                    out.flush();
                    out.close();
                    String result = new String(out.toByteArray());
                    T t = bindData(result);
                    return postResult(t);
                }
            } else {
                throw new AppException(AppException.ErrorType.SERVER, status, conn.getResponseMessage());
            }
        } catch (Exception e) {
            throw new AppException(AppException.ErrorType.SERVER, e.getMessage());
        }
    }

    @Override
    public T postResult(T t) {
        return t;
    }

    @Override
    public void onProgressUpdate(long currentLen, long totalLen) {
    }

    @Override
    public void cancel(boolean b) {
        this.isCancel = b;
    }

    public abstract T bindData(String result);

    public ICallBack setFilePath(String s) {
        this.path = s;
        return this;
    }


    boolean checkIsCancel() {
        return this.isCancel;
    }
}
