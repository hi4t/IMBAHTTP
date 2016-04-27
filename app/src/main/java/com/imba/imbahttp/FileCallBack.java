package com.imba.imbahttp;

/**
 * Created by zace on 2016/4/27.
 */
public abstract class FileCallBack extends AbstractCallBack<String> {

    @Override
    public String bindData(String result) {
        return result;
    }
}
