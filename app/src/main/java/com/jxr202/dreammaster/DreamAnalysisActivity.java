package com.jxr202.dreammaster;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jxr202.colorful_ui.TitleBar;
import com.jxr202.dreammaster.utils.ImageUtil;
import com.jxr202.dreammaster.utils.NetworkUtil;
import com.jxr202.dreammaster.utils.YoumiSdkHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DreamAnalysisActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.dreamList)
    TextView mDreamList;

    private Dream mDream;

    private static final String TAG = "jxr202";

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_analysis);
        ButterKnife.bind(this);
        initTitleBar();
        initData();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                YoumiSdkHelper.showSlideableSpotAd(DreamAnalysisActivity.this);
            }
        }, 1000);

    }

    private void initTitleBar() {
        mTitleBar.setOnTitleBarClickListener(new TitleBar.SimpleTitleBarClickListener() {
            @Override
            public void onLeftImageClick() {
                onBackPressed();
            }

            @Override
            public void onRightImageClick() {
                if (!isFastDoubleClick()) {
                    if (NetworkUtil.isNetworkConnected(DreamAnalysisActivity.this)) {
                        ImageUtil.snapShotWithoutStatusBar(DreamAnalysisActivity.this);
                        //doShare(mShareUtil);
                    } else {
                        Toast.makeText(DreamAnalysisActivity.this, R.string.no_network, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        mDream = (Dream) intent.getSerializableExtra("dream");
        //title.setText(mDream.title);
        mTitleBar.setTitleText(mDream.title);
        StringBuilder sb = new StringBuilder();
        for (String temp : mDream.list) {
            sb.append(temp).append("\n\n");
        }
        mDreamList.setText(sb);
    }


    @Override
    public void onBackPressed() {
        boolean onBackPressed = YoumiSdkHelper.onBackPressed(this);
        Log.i(TAG, "onBackPressed: " + onBackPressed);
        if (!onBackPressed) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        YoumiSdkHelper.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        YoumiSdkHelper.onStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        YoumiSdkHelper.onDestroy(this);
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
