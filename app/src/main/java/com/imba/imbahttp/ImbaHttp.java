package com.imba.imbahttp;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by zace on 2015/4/26.
 */
public class ImbaHttp {

    public static HttpURLConnection excute(Request request) throws Exception {
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


    private static HttpURLConnection get(Request request) throws Exception {

        HttpURLConnection conn = (HttpURLConnection) new URL(request.getUrl()).openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(15 * 1000);
        conn.setReadTimeout(15 * 1000);
        addHeader(conn, request.getHeader());

        return conn;
    }


    private static HttpURLConnection post(Request request) throws Exception {

        HttpURLConnection conn = (HttpURLConnection) new URL(request.getUrl()).openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(15 * 1000);
        conn.setReadTimeout(15 * 1000);

        addHeader(conn, request.getHeader());

        OutputStream os = conn.getOutputStream();
        os.write(request.getContent().getBytes());

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
