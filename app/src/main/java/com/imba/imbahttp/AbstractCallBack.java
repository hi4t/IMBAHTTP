package com.imba.imbahttp;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by zace on 2016/4/26.
 */
public abstract class AbstractCallBack<T> implements ICallBack<T> {

    private String path;

    @Override
    public T parsr(HttpURLConnection conn) throws Exception {
        int status = conn.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            OutputStream out = null;

            if (!TextUtils.isEmpty(path)) {
                out = new FileOutputStream(path);
            } else {
                out = new ByteArrayOutputStream();
            }

            InputStream is = conn.getInputStream();
            byte[] buffer = new byte[2048];

            int len;

            while ((len = is.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            is.close();
            out.flush();
            out.close();

            if (!TextUtils.isEmpty(path)) {
                bindData(path);
            } else {
                String result = new String(((ByteArrayOutputStream) out).toByteArray());
                bindData(result);
            }
        }

        return null;
    }

    public abstract T bindData(String result);

    public ICallBack setFilePath(String s) {
        this.path = s;
        return this;
    }
}
