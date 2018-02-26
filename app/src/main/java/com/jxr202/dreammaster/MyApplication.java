package com.jxr202.dreammaster;

import android.app.Application;

/**
 * Created by Jxr35 on 2018/2/26
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        OkHttpUtils.initHttp(this);
    }

}
