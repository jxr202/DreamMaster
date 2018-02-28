package com.jxr202.dreammaster;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.jxr202.colorful_ui.TitleBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SearchActivity extends BaseActivity {

    private static final String TAG = "jxr202";

    @BindView(R.id.search)
    EditText mSearch;
    @BindView(R.id.history)
    ListView mHistory;

    private SearchAdapter mAdapter;
    private List<Dream> mDreams;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initDreams();
        initHandler();
        initTitleBar();
        initEditText();
        initListView();
    }

    private void initDreams() {
        mDreams = AppCache.getDreams();
        if (mDreams.size() == 0) {
            mDreams = AppCache.getDreamsNetwork();
        }
    }

    private void initTitleBar() {
        ((TitleBar) findViewById(R.id.title_bar)).setOnTitleBarClickListener(
                new TitleBar.SimpleTitleBarClickListener() {
                    @Override
                    public void onLeftImageClick() {
                        finish();
                    }
                }
        );
    }

    private void initEditText() {
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG, "beforeTextChanged.. s: " + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "onTextChanged.. s: " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "afterTextChanged.. s: " + s);
                if (!TextUtils.isEmpty(s)) {
                    Message message = Message.obtain();
                    message.what = 111;
                    message.obj = s.toString();
                    mHandler.sendMessage(message);
                } else {
                    mHandler.sendEmptyMessage(113);
                }
            }
        });
    }

    private void initListView() {
        mAdapter = new SearchAdapter(this);
        mAdapter.setDreams(mDreams);
        mHistory.setAdapter(mAdapter);
        mHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dream dream = mDreams.get(position);
                AppCache.addDream(dream);
                Intent intent = new Intent(SearchActivity.this, DreamAnalysisActivity.class);
                intent.putExtra("dream", dream);
                startActivity(intent);
            }
        });
    }

    private void initHandler() {
        mHandler = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                if (msg.what == 111) {
                    queryDreams((String) msg.obj);
                } else if (msg.what == 112) {
                    updateListView((String) msg.obj);
                } else if (msg.what == 113) {
                    updateListViewFromCache();
                }
                return false;
            }
        });
    }

    private void queryDreams(String word) {
        String uri = URLs.query + "&q=" + word + "&full=1";
        OkHttpUtils.get(uri, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    ResponseBody resp = response.body();
                    if (resp != null) {
                        String body = resp.string();
                        Log.i(TAG, "获取数据成功.. body: " + body);
                        Message message = Message.obtain();
                        message.what = 112;
                        message.obj = body;
                        mHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateListView(String body) {
        try {
            JSONObject object = new JSONObject(body);
            int errorCode = object.getInt("error_code");
            String result = object.getString("result");
            if (errorCode == 0 && result != null) {
                JSONArray array = new JSONArray(result);
                mDreams.clear();
                for (int i = 0; i < array.length(); i ++) {
                    JSONObject jsonDream = array.getJSONObject(i);
                    Dream dream = new Dream();
                    dream.id = jsonDream.getString("id");
                    dream.title = jsonDream.getString("title");
                    dream.des = jsonDream.getString("des");
                    JSONArray list = jsonDream.getJSONArray("list");
                    dream.list = new String[list.length()];
                    for (int j = 0; j < list.length(); j ++) {
                        dream.list[j] = list.getString(j);
                    }
                    Log.i(TAG, dream.toString());
                    mDreams.add(dream);
                }
                mAdapter.setDreams(mDreams);
                mAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateListViewFromCache() {
        mDreams = AppCache.getDreams();
        mAdapter.setDreams(mDreams);
        mAdapter.notifyDataSetChanged();
    }

}
