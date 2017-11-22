package com.sjy.pickphotos.pickphotos.compress;


import android.content.Context;
import android.widget.Toast;

import com.sjy.pickphotos.pickphotos.ui.PicActivity;

import java.io.File;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 压缩图片
 * Created by sjy on 2017/11/20.
 */

public class CompressUtil {

    public static void compress(final OnCompressListener onCompressListener, String path, final Context mContext){
        Luban.with(mContext)
                .load(path)                                   // 传人要压缩的图片列表
                .ignoreBy(100)                                                    // 忽略不压缩图片的大小
                .setTargetDir(mContext.getExternalCacheDir().getAbsolutePath())   // 设置压缩后文件存储位置，缓存目录
                .setCompressListener(new top.zibin.luban.OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        onCompressListener.onStart();
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        onCompressListener.onSuccess(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        onCompressListener.onError(e);
                    }
                }).launch();    //启动压缩
    }


    public interface OnCompressListener{
        void onStart();
        void onSuccess(File file);
        void onError(Throwable e);
    }
}
