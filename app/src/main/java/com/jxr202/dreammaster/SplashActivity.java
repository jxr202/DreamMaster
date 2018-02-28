package com.jxr202.dreammaster;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.jxr202.dreammaster.appdata.AppConfig;
import com.jxr202.dreammaster.utils.PermissionHelper;

import abc.abc.abc.AdManager;
import abc.abc.abc.nm.cm.ErrorCode;
import abc.abc.abc.nm.sp.SplashViewSettings;
import abc.abc.abc.nm.sp.SpotListener;
import abc.abc.abc.nm.sp.SpotManager;
import abc.abc.abc.nm.sp.SpotRequestListener;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "jxr202";

    private PermissionHelper mPermissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 移除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        // 当系统为6.0以上时，需要申请权限
        mPermissionHelper = new PermissionHelper(this);
        mPermissionHelper.setOnApplyPermissionListener(new PermissionHelper.OnApplyPermissionListener() {
            @Override
            public void onAfterApplyAllPermission() {
                Log.i(TAG, "All of requested permissions has been granted, so run app logic.");
                runApp();
            }
        });
        if (Build.VERSION.SDK_INT < 23) {
            // 如果系统版本低于23，直接跑应用的逻辑
            Log.i(TAG, "The api level of system is lower than 23, so run app logic directly.");
            runApp();
        } else {
            // 如果权限全部申请了，那就直接跑应用逻辑
            if (mPermissionHelper.isAllRequestedPermissionGranted()) {
                Log.d(TAG, "All of requested permissions has been granted, so run app logic directly.");
                runApp();
            } else {
                // 如果还有权限为申请，而且系统版本大于23，执行申请权限逻辑
                Log.i(TAG, "Some of requested permissions hasn't been granted, so apply permissions first.");
                mPermissionHelper.applyPermissions();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 跑应用的逻辑
     */
    private void runApp() {
        //初始化SDK
        AdManager.getInstance(this).init(AppConfig.APP_ID, AppConfig.APP_SECRET, true);
        preloadAd();
        setupSplashAd(); // 如果需要首次展示开屏，请注释掉本句代码
    }

    /**
     * 预加载广告
     */
    private void preloadAd() {
        // 注意：不必每次展示插播广告前都请求，只需在应用启动时请求一次
        SpotManager.getInstance(this).requestSpot(new SpotRequestListener() {
            @Override
            public void onRequestSuccess() {
                Log.i(TAG, "请求插播广告成功");
                //				// 应用安装后首次展示开屏会因为本地没有数据而跳过
                //              // 如果开发者需要在首次也能展示开屏，可以在请求广告成功之前展示应用的logo，请求成功后再加载开屏
                //				setupSplashAd();
            }

            @Override
            public void onRequestFailed(int errorCode) {
                Log.i(TAG, "请求插播广告失败，errorCode: " + errorCode);
                switch (errorCode) {
                    case ErrorCode.NON_NETWORK:
                        showToast("网络异常");
                        break;
                    case ErrorCode.NON_AD:
                        showToast("暂无视频广告");
                        break;
                    default:
                        showToast("请稍后再试");
                        break;
                }
            }
        });
    }

    /**
     * 设置开屏广告
     */
    private void setupSplashAd() {
        // 创建开屏容器
        final RelativeLayout splashLayout = findViewById(R.id.rl_splash);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ABOVE, R.id.view_divider);

        // 对开屏进行设置
        SplashViewSettings splashViewSettings = new SplashViewSettings();
        //		// 设置是否展示失败自动跳转，默认自动跳转
        //		splashViewSettings.setAutoJumpToTargetWhenShowFailed(false);
        // 设置跳转的窗口类
        splashViewSettings.setTargetClass(MainActivity.class);
        // 设置开屏的容器
        splashViewSettings.setSplashViewContainer(splashLayout);

        // 展示开屏广告
        SpotManager.getInstance(this).showSplash(this, splashViewSettings, new SpotListener() {

            @Override
            public void onShowSuccess() {
                Log.i(TAG, "开屏展示成功");
            }

            @Override
            public void onShowFailed(int errorCode) {
                Log.i(TAG, "开屏展示失败");
                switch (errorCode) {
                    case ErrorCode.NON_NETWORK:
                        Log.i(TAG, "网络异常");
                        break;
                    case ErrorCode.NON_AD:
                        Log.i(TAG, "暂无开屏广告");
                        break;
                    case ErrorCode.RESOURCE_NOT_READY:
                        Log.i(TAG, "开屏资源还没准备好");
                        break;
                    case ErrorCode.SHOW_INTERVAL_LIMITED:
                        Log.i(TAG, "开屏展示间隔限制");
                        break;
                    case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                        Log.i(TAG, "开屏控件处在不可见状态");
                        break;
                    default:
                        Log.i(TAG, "errorCode: " + errorCode);
                        break;
                }
            }

            @Override
            public void onSpotClosed() {
                Log.i(TAG, "开屏被关闭");
            }

            @Override
            public void onSpotClicked(boolean isWebPage) {
                Log.i(TAG, "开屏被点击");
                Log.i(TAG, "是否是网页广告？" + (isWebPage ? "是" : "不是"));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 开屏展示界面的 onDestroy() 回调方法中调用
        SpotManager.getInstance(this).onDestroy();
    }

}
