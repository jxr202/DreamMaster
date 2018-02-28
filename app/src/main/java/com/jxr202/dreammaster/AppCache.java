package com.jxr202.dreammaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Jxr35 on 2018/2/27
 */

public class AppCache {

    private static final String TAG = "jxr202";
    private static final String DATA_NAME = "dream_master_data";
    private static Gson mGson = new Gson();
    private static SharedPreferences mAppDataSpf;

    public static void initCache(Context context) {
        mAppDataSpf = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
    }

    public static void setDreamsNetwork(String body) {
        List<Dream> dreams = mGson.fromJson(body, new TypeToken<List<Dream>>() {
        }.getType());
        for (Dream dream : dreams) {
            Log.i(TAG, "dream: " + dream);
        }
        String dreamCache = mGson.toJson(dreams);
        mAppDataSpf.edit().putString("dream_cache_network", dreamCache).apply();
    }

    public static List<Dream> getDreamsNetwork() {
        List<Dream> dreams = new ArrayList<>();
        String dreamCache = mAppDataSpf.getString("dream_cache_network", "");
        if (dreamCache.equals("")) {
            return dreams;
        }
        dreams = mGson.fromJson(dreamCache, new TypeToken<List<Dream>>(){}.getType());
        return dreams;
    }

    public static void addDream(Dream dream) {
        List<Dream> dreamCaches = getDreams();
        if (dreamCaches.contains(dream)) {
            dreamCaches.remove(dream);
            dreamCaches.add(0, dream);
        } else if (dreamCaches.size() > 10) {
            dreamCaches.remove(9);
            dreamCaches.add(0, dream);
        } else {
            dreamCaches.add(0, dream);
        }
        String dreamCache = mGson.toJson(dreamCaches);
        mAppDataSpf.edit().putString("dream_cache", dreamCache).apply();
    }

    public static List<Dream> getDreams() {
        List<Dream> dreams = new ArrayList<>();
        String dreamCache = mAppDataSpf.getString("dream_cache", "");
        if (dreamCache.equals("")) {
            return dreams;
        }
        dreams = mGson.fromJson(dreamCache, new TypeToken<List<Dream>>(){}.getType());
        return dreams;
    }

}
