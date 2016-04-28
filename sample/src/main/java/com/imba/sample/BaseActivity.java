package com.imba.sample;

import android.support.v7.app.AppCompatActivity;

import com.imba.exception.AppException;
import com.imba.imbalibrary.OnGlobleExceptionListener;

/**
 * Created by zace on 2016/4/28.
 */
public class BaseActivity extends AppCompatActivity implements OnGlobleExceptionListener {
    @Override
    public void handleException(AppException e) {
        if (e.getStatus() == 403) {
            //TODO
        }
    }
}
