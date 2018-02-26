package com.jxr202.dreammaster;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class MainActivity extends Activity {

    private static final String TAG = "jxr202";

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
    }

    @OnClick({R.id.lab_01, R.id.lab_02, R.id.lab_03, R.id.lab_04, R.id.lab_05, R.id.lab_06, R.id.lab_07, R.id.lab_08, R.id.lab_09, R.id.lab_10})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lab_01: {
                Log.i(TAG, "点击了身体类");

                String url = URLs.query + "&q=" + encode("蛇");

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

                break;
            }
            case R.id.lab_02:
                break;
            case R.id.lab_03:
                break;
            case R.id.lab_04:
                break;
            case R.id.lab_05:
                break;
            case R.id.lab_06:
                break;
            case R.id.lab_07:
                break;
            case R.id.lab_08:
                break;
            case R.id.lab_09:
                break;
            case R.id.lab_10:
                break;
        }
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
