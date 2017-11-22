package com.sjy.pickphotos.pickphotos.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by sjy on 2017/5/8.
 */

public class PermissionResult {
    private Context mContext;
    private Activity activity;
    private File output;
    private Uri imageUri;
    public static final int CROP_PHOTO=0;

    public PermissionResult(Context mContext, Activity activity) {
        this.activity=activity;
        this.mContext = mContext;
    }


    /**
     * 拍照
     */
    public void takePhoto(){
        /**
         * 最后一个参数是文件夹的名称，可以随便起
         */
        File file=new File(Environment.getExternalStorageDirectory(),"拍照");
        if(!file.exists()){
            file.mkdir();
        }
        /**
         * 这里将时间作为不同照片的名称
         */
        output=new File(file, System.currentTimeMillis()+".jpg");

        /**
         * 如果该文件夹已经存在，则删除它，否则创建一个
         */
        try {
            if (output.exists()) {
                output.delete();
            }
            output.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 隐式打开拍照的Activity，并且传入CROP_PHOTO常量作为拍照结束后回调的标志
         * 将文件转化为uri
         */
        imageUri = Uri.fromFile(output);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intent, CROP_PHOTO);
    }



}
