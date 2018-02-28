package com.jxr202.dreammaster;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
        loadDreamsNetwork();
    }

    public static void getDream() {

    }

    /**
     * get请求
     **/
    static void get(String url, Callback callback) {
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

    public static void loadDreamsNetwork() {
        Log.i(TAG, "loadDreamsNetwork..");
        OkHttpUtils.get(URLs.dreamCache, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "loadDreamsNetwork.. onFailure: e" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody resp = response.body();
                Log.i(TAG, "loadDreamsNetwork.. onResponse: response" + response.code());
                if (resp != null) {
                    String body = resp.string();
                    AppCache.setDreamsNetwork(body);
                }
            }
        });
    }


}
