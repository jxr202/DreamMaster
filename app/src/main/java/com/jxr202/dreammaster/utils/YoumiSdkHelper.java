package com.jxr202.dreammaster.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import abc.abc.abc.nm.bn.BannerManager;
import abc.abc.abc.nm.bn.BannerViewListener;
import abc.abc.abc.nm.cm.ErrorCode;
import abc.abc.abc.nm.sp.SpotListener;
import abc.abc.abc.nm.sp.SpotManager;

/**
 * Created by Jxr35 on 2018/2/28
 */

public class YoumiSdkHelper {

    private static final String TAG = "jxr202";

    /**
     * 设置原生插屏广告
     */
    public static void showNativeSpotAd(ViewGroup viewGroup, final Context context) {

        //	设置插屏图片类型，默认竖图
        //	横图
        SpotManager.getInstance(context).setImageType(SpotManager.IMAGE_TYPE_HORIZONTAL);
        //  竖图
        //SpotManager.getInstance(this).setImageType(SpotManager.IMAGE_TYPE_VERTICAL);

        // 设置动画类型，默认高级动画
        // 无动画
        // SpotManager.getInstance(mContext).setAnimationType(SpotManager.ANIMATION_TYPE_NONE);
        // 简单动画
        // SpotManager.getInstance(mContext).setAnimationType(SpotManager.ANIMATION_TYPE_SIMPLE);
        // 高级动画
        SpotManager.getInstance(context).setAnimationType(SpotManager.ANIMATION_TYPE_ADVANCED);

        View nativeSpotView = SpotManager.getInstance(context).getNativeSpot(context, new SpotListener() {
            @Override
            public void onShowSuccess() {
                Log.i(TAG, "原生插屏展示成功");
            }

            @Override
            public void onShowFailed(int errorCode) {
                Log.i(TAG, "原生插屏展示失败");
                switch (errorCode) {
                    case ErrorCode.NON_NETWORK:
                        showToast(context, "网络异常");
                        break;
                    case ErrorCode.NON_AD:
                        showToast(context, "暂无原生插屏广告");
                        break;
                    case ErrorCode.RESOURCE_NOT_READY:
                        showToast(context, "原生插屏资源还没准备好");
                        break;
                    case ErrorCode.SHOW_INTERVAL_LIMITED:
                        showToast(context, "请勿频繁展示");
                        break;
                    case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                        showToast(context, "请设置插屏为可见状态");
                        break;
                    default:
                        showToast(context, "请稍后再试");
                        break;
                }
            }

            @Override
            public void onSpotClosed() {
                Log.i(TAG, "原生插屏被关闭");
            }

            @Override
            public void onSpotClicked(boolean isWebPage) {
                Log.i(TAG, "原生插屏被点击");
                Log.i(TAG, "是否是网页广告？" + (isWebPage ? "是" : "不是"));
            }
        });
        if (nativeSpotView != null) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            viewGroup.removeAllViews();
            // 添加原生插屏控件到容器中
            viewGroup.addView(nativeSpotView, layoutParams);
            if (viewGroup.getVisibility() != View.VISIBLE) {
                viewGroup.setVisibility(View.VISIBLE);
            }
        }
    }

    public static boolean hideNativeSpotAd(ViewGroup viewGroup) {
        if (viewGroup.getVisibility() != View.GONE) {
            viewGroup.removeAllViews();
            //viewGroup.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    /**
     * 设置轮播插屏广告
     */
    public static void showSlideableSpotAd(final Context mContext) {

        // 设置插屏图片类型，默认竖图
        // 横图
        // SpotManager.getInstance(mContext).setImageType(SpotManager.IMAGE_TYPE_HORIZONTAL);
        // 竖图
        SpotManager.getInstance(mContext).setImageType(SpotManager.IMAGE_TYPE_VERTICAL);

        // 设置动画类型，默认高级动画
        // 无动画
        // SpotManager.getInstance(mContext).setAnimationType(SpotManager.ANIMATION_TYPE_NONE);
        // 简单动画
        // SpotManager.getInstance(mContext).setAnimationType(SpotManager.ANIMATION_TYPE_SIMPLE);
        // 高级动画
        SpotManager.getInstance(mContext).setAnimationType(SpotManager.ANIMATION_TYPE_ADVANCED);

        // 展示轮播插屏广告
        SpotManager.getInstance(mContext).showSlideableSpot(mContext, new SpotListener() {

            @Override
            public void onShowSuccess() {
                Log.i(TAG, "轮播插屏展示成功");
            }

            @Override
            public void onShowFailed(int errorCode) {
                Log.e(TAG, "轮播插屏展示失败");
                switch (errorCode) {
                    case ErrorCode.NON_NETWORK:
                        showToast(mContext, "网络异常");
                        break;
                    case ErrorCode.NON_AD:
                        showToast(mContext, "暂无轮播插屏广告");
                        break;
                    case ErrorCode.RESOURCE_NOT_READY:
                        showToast(mContext, "轮播插屏资源还没准备好");
                        break;
                    case ErrorCode.SHOW_INTERVAL_LIMITED:
                        showToast(mContext, "请勿频繁展示");
                        break;
                    case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                        showToast(mContext, "请设置插屏为可见状态");
                        break;
                    default:
                        showToast(mContext, "请稍后再试");
                        break;
                }
            }

            @Override
            public void onSpotClosed() {
                Log.i(TAG, "轮播插屏被关闭");
            }

            @Override
            public void onSpotClicked(boolean isWebPage) {
                Log.i(TAG, "轮播插屏被点击");
                Log.i(TAG, "是否是网页广告？" + (isWebPage ? "是" : "不是"));
            }
        });
    }


    public static void showBannerViewAd(ViewGroup viewGroup, Context context) {
        // 获取广告条
        View bannerView = BannerManager.getInstance(context).getBannerView(context, new BannerViewListener() {
            @Override
            public void onRequestSuccess() {
                Log.i(TAG, "轮播插屏展示成功");
            }

            @Override
            public void onSwitchBanner() {

            }

            @Override
            public void onRequestFailed() {

            }
        });

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        viewGroup.removeAllViews();
        // 将广告条加入到布局中
        viewGroup.addView(bannerView, layoutParams);
    }

    public static void hideBannerViewAd(Context context) {
        BannerManager.getInstance(context).onDestroy();
    }


    public static boolean onBackPressed(Context mContext) {
        // 点击后退关闭轮播插屏广告
        if (SpotManager.getInstance(mContext).isSlideableSpotShowing()) {
            SpotManager.getInstance(mContext).hideSlideableSpot();
            return true;
        }
        return false;
    }

    public static void onPause(Context mContext) {
        // 轮播插屏广告
        SpotManager.getInstance(mContext).onPause();
    }

    public static void onStop(Context mContext) {
        // 轮播插屏广告
        SpotManager.getInstance(mContext).onStop();
    }

    public static void onDestroy(Context mContext) {
        // 轮播插屏广告
        SpotManager.getInstance(mContext).onDestroy();
    }

    private static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
