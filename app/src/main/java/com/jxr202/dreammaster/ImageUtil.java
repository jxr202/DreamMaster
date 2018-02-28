package com.jxr202.dreammaster;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 图片处理工具类
 */
public class ImageUtil {

    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        if (bp == null)
            return null;
        saveMyBitmap(bp, "dream.jpg");
        return bp;

    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    // 保存图片到sd卡
    public static boolean saveMyBitmap(Bitmap mBitmap, String fileName) {
        boolean isSave = true;
        File appDir = new File("DreamMaster/share/");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File f = new File(appDir, fileName);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            isSave = false;
            e.printStackTrace();
        }
        try {
            mBitmap.compress(CompressFormat.JPEG, 100, fOut);
            if (fOut != null) {
                fOut.flush();
            }
            if (fOut != null) {
                fOut.close();
            }
        } catch (Exception e) {
            isSave = false;
            e.printStackTrace();
        }
        return isSave;
    }


    public static Bitmap getBitmapFromView(View view, int width, int height) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View
                .MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View
                .MeasureSpec.EXACTLY);
        view.measure(widthSpec, heightSpec);
        view.layout(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config
                .ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static Bitmap getBitmapFromView(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap result = view.getDrawingCache();
        return result;
    }

}
