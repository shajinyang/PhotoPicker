package com.sjy.pickphotos.pickphotos.takephoto;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.sjy.pickphotos.pickphotos.BuildConfig;
import com.sjy.pickphotos.pickphotos.util.SConstants;

import java.io.File;

/**
 * 拍照
 * Created by sjy on 2017/11/22.
 */

public class TakePhotoUtil {

    public static final int  REQUEST_CAMERA=0x223;
    static File outputFile;

    public static void camera(Activity activity){
        outputFile = new File(activity.getExternalCacheDir(), System.currentTimeMillis()    + ".jpg");
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdir();
        }
        Uri contentUri = FileProvider.getUriForFile(activity,
                SConstants.getmContext().getPackageName() + ".fileProvider", outputFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        activity.startActivityForResult(intent, REQUEST_CAMERA);
    }

    public static File onSuccess(){
        return outputFile;
    }


}
