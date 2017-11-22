package com.sjy.pickphotos.pickphotos.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by sjy on 2017/11/20.
 */

public class Util {
    /**
     * 获取缓存目录
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

}
