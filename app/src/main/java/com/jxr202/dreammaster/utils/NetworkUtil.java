package com.jxr202.dreammaster.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 *
 * 网络请求辅助类
 * @author yuanhang
 */
public class NetworkUtil {


	/**
	 * 检测网络是否可用
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}


	public static boolean isWifiConnected(Context mContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getState() == NetworkInfo.State.CONNECTED && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }


}
