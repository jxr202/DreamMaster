package com.jxr202.dreammaster;

import android.app.Application;

import com.jxr202.dreammaster.appdata.AppCache;
import com.jxr202.dreammaster.utils.OkHttpUtils;
import com.tencent.bugly.crashreport.CrashReport;

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
        AppCache.initCache(this);
        OkHttpUtils.initHttp(this);
        CrashReport.initCrashReport(getApplicationContext(), "1189c19735", false);

        /*SpotManager.getInstance(this).requestSpot(new SpotRequestListener() {
            @Override
            public void onRequestSuccess() {

            }

            @Override
            public void onRequestFailed(int i) {

            }
        });*/
    }

}
