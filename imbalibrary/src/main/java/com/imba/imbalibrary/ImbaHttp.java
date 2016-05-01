package com.imba.imbalibrary;

import android.text.TextUtils;
import android.webkit.URLUtil;

import com.imba.exception.AppException;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by zace on 2015/4/26.
 */
public class ImbaHttp {

    public static HttpURLConnection excute(Request request) throws AppException {

        if (!URLUtil.isNetworkUrl(request.getUrl())) {
            throw new AppException(AppException.ErrorType.NET, "url:" + request.getUrl() + "is not valid");
        }

        switch (request.getMethod()) {
            case GET:
            case DELETE:
                return get(request);
            case PUT:
            case POST:
                return post(request);
        }
        return null;
    }


    private static HttpURLConnection get(Request request) throws AppException {

        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) new URL(request.getUrl()).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(15 * 1000);
            conn.setReadTimeout(15 * 1000);
            addHeader(conn, request.getHeader());
        } catch (InterruptedIOException e) {
            throw new AppException(AppException.ErrorType.TIMEOUT, e.getMessage());
        } catch (Exception e) {
            throw new AppException(AppException.ErrorType.SERVER, e.getMessage());
        }

        return conn;
    }


    private static HttpURLConnection post(Request request) throws AppException {

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(request.getUrl()).openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15 * 1000);
            conn.setReadTimeout(15 * 1000);

            addHeader(conn, request.getHeader());

            OutputStream os = conn.getOutputStream();

            if (!TextUtils.isEmpty(request.getFilePath())) {
                UploadUtil.upload(os, request.getFilePath(),
                        request.getUploadListener() == null ? request.getUploadListener() : null);
            } else if (request.getEntities() != null) {
                UploadUtil.upload(os, request.getContent(), request.getEntities(),
                        request.getUploadListener() == null ? request.getUploadListener() : null);
            } else if (!TextUtils.isEmpty(request.getContent())) {
                os.write(request.getContent().getBytes());
            } else {
                throw new AppException(AppException.ErrorType.MANUAL, "the request content can not be null");
            }


        } catch (InterruptedIOException e) {
            throw new AppException(AppException.ErrorType.TIMEOUT, e.getMessage());
        } catch (Exception e) {
            throw new AppException(AppException.ErrorType.SERVER, e.getMessage());
        }

        return conn;

    }

    private static void addHeader(HttpURLConnection conn, Map<String, String> header) {

        if (header == null || header.size() == 0)
            return;

        for (Map.Entry<String, String> entry : header.entrySet()) {
            conn.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }


}
