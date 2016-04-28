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

    @Override
    public T parsr(HttpURLConnection conn, OnProgressUpdateListener listener) throws AppException {
        try {
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
                        out.write(buffer, 0, len);
                        currentLen += len;
                        if (listener != null) {
                            listener.onProgressUpdaet(currentLen, totalLen);
                        }

                    }
                    is.close();
                    out.flush();
                    out.close();
                    return bindData(path);

                } else {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    InputStream is = conn.getInputStream();
                    byte[] buffer = new byte[2048];

                    int len;

                    while ((len = is.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                    is.close();
                    out.flush();
                    out.close();
                    String result = new String(out.toByteArray());
                    return bindData(result);
                }
            } else {
                throw new AppException(status, conn.getResponseMessage());
            }
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
    }

    @Override
    public void onProgressUpdate(long currentLen, long totalLen) {
    }

    public abstract T bindData(String result);

    public ICallBack setFilePath(String s) {
        this.path = s;
        return this;
    }

}
