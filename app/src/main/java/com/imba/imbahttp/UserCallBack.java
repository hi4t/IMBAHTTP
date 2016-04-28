package com.imba.imbahttp;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by zace on 2015/4/26.
 */
public abstract class UserCallBack extends AbstractCallBack<User> {
    @Override
    public User bindData(String result) {
        Gson gson = new Gson();
        Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return gson.fromJson(result, type);
    }
}
