package com.imba.imbalibrary;

import android.os.AsyncTask;

import java.net.HttpURLConnection;

/**
 * Created by zace on 2015/4/26.
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
            HttpURLConnection connection = ImbaHttp.excute(request);
            return request.getCallBack().parsr(connection, new OnProgressUpdateListener() {
                @Override
                public void onProgressUpdaet(int curLen, int totalLen) {
                    publishProgress(curLen, totalLen);
                }
            });
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


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        request.getCallBack().onProgressUpdate(values[0], values[1]);

    }

}
