package com.jxr202.dreammaster;

import android.content.Context;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Jxr35 on 2018/2/26
 */

public class OkHttpUtils {

    public static final String TAG = "jxr202";

    static OkHttpClient mOkHttpClient;

    static Context mContext;

    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 初始化http
     **/
    public static void initHttp(Context mContexts) {
        mContext = mContexts;
        mOkHttpClient = new OkHttpClient();
    }

    public static void getDream() {

    }

    /**
     * get请求
     **/
    public static void get(String url, Callback callback) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            mOkHttpClient.newCall(request).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * post请求
     **/
    public static void post(String url, String json, Callback callback) {
        try {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            mOkHttpClient.newCall(request).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
