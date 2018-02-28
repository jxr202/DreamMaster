package com.jxr202.dreammaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    private static final String TAG = "jxr202";

    @BindView(R.id.search)
    ImageView mSearch;
    @BindView(R.id.lab_01)
    TextView lab01;
    @BindView(R.id.lab_02)
    TextView lab02;
    @BindView(R.id.lab_03)
    TextView lab03;
    @BindView(R.id.lab_04)
    TextView lab04;
    @BindView(R.id.lab_05)
    TextView lab05;
    @BindView(R.id.lab_06)
    TextView lab06;
    @BindView(R.id.lab_07)
    TextView lab07;
    @BindView(R.id.lab_08)
    TextView lab08;
    @BindView(R.id.lab_09)
    TextView lab09;
    @BindView(R.id.lab_10)
    TextView lab10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getAll();
    }

    @OnClick({R.id.search, R.id.lab_01, R.id.lab_02, R.id.lab_03, R.id.lab_04, R.id.lab_05, R.id.lab_06, R.id.lab_07, R.id.lab_08, R.id.lab_09, R.id.lab_10})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search: {
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.lab_01:
            case R.id.lab_02:
            case R.id.lab_03:
            case R.id.lab_04:
            case R.id.lab_05:
            case R.id.lab_06:
            case R.id.lab_07:
            case R.id.lab_08:
            case R.id.lab_09:
            case R.id.lab_10:
                Intent intent = new Intent(this, SearchActivity.class);
                intent.putExtra("id", view.getId());
                startActivity(intent);
                break;
        }
    }

    private void getAll() {
        String url = URLs.url + "&fid=89";
        OkHttpUtils.get(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "获取数据失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                String body = response.body().string();
                Log.i(TAG, "获取数据成功.. code: " + code + ", body: " + body);
            }
        });
    }

    private void getData(int cid) {
        String url = URLs.query + "&q=' '&cid=" + cid;
        OkHttpUtils.get(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "获取数据失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                String body = response.body().string();
                Log.i(TAG, "获取数据成功.. code: " + code + ", body: " + body);
            }
        });
    }

    private String encode(String data) {
        try {
            return URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
