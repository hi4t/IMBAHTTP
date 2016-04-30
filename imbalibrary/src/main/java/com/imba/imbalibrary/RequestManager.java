package com.imba.imbalibrary;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zace on 2015/4/30.
 */
public class RequestManager {

    private static RequestManager requestManager;
    private HashMap<String, ArrayList<Request>> requestList = new HashMap<>();

    public static RequestManager getInstance() {
        if (requestManager == null) {
            requestManager = new RequestManager();
            return requestManager;
        }
        return requestManager;
    }

    private RequestManager() {
    }


    private void performRequest(Request request) {
        RequestTask task = new RequestTask(request);
        task.execute();

        if (!requestList.containsKey(request.getTag())) {
            ArrayList<Request> list = new ArrayList<>();
            list.add(request);
            requestList.put(request.getTag(), list);
        }

        requestList.get(request.getTag()).add(request);
    }

    private void cancelRequest(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }

        if (requestList.containsKey(tag)) {
            ArrayList<Request> list = requestList.remove(tag);
            for (Request request : list) {
                if (!request.checkIsCanceled() && tag.equals(request.getTag())) {
                    request.cancel();
                }

            }
        }
    }

    private void cancelAll() {
        for (Map.Entry<String, ArrayList<Request>> entry : requestList.entrySet()) {
            ArrayList<Request> list = entry.getValue();
            for (Request request : list) {
                if (!request.checkIsCanceled()) {
                    request.cancel();
                }
            }
        }
    }


}
