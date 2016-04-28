package com.imba.imbalibrary;

import com.imba.exception.AppException;

/**
 * Created by zace on 2016/4/28.
 */
public interface OnGlobleExceptionListener {

    void handleException(AppException e);
}
