package com.sjy.pickphotos.pickphotos.crop.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;

/**
 * android尺寸处理工具类
 * Created by sjy on 2017/5/6.
 */

public class DisplayUtil {
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context mContext, float pxValue) {
        float scale=mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context mContext,float dipValue) {
        float scale=mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context mContext,float pxValue) {
        float fontScale=mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context mContext,float spValue) {
        float fontScale=mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }



    /**
     * 获取软键盘高度
     */
    public static int getSpftInputHeight(Activity mActivity){
        Rect r = new Rect();
        /*
        * decorView是window中的最顶层view，可以从window中通过getDecorView获取到decorView。
        * 通过decorView获取到程序显示的区域，包括标题栏，但不包括状态栏。
        */
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //获取屏幕的高度
        int screenHeight = mActivity.getWindow().getDecorView().getRootView().getHeight();
        //计算软件盘的高度
        int softInputHeight = screenHeight - r.bottom;
        return  softInputHeight;
    }
}