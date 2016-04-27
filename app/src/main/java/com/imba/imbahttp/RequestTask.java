package com.imba.imbahttp;

import android.os.AsyncTask;

import java.net.HttpURLConnection;

/**
 * Created by zace on 2016/4/26.
 */
public class RequestTask extends AsyncTask<Void, Integer, Object> {

    private Request request;

    public RequestTask(Request request) {
        this.request = request;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Void... params) {
        try {
            HttpURLConnection connection =  ImbaHttp.excute(request);
            return request.getCallBack().parsr(connection);
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o instanceof Exception) {
            request.getCallBack().onFailure((Exception) o);
        } else {
            request.getCallBack().onSuccess(o);
        }
    }
}
