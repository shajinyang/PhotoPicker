package com.sjy.pickphotos.pickphotos.crop;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;

/**
 * 裁剪图片
 * Created by sjy on 2017/11/20.
 */

public class CropUtil {
    public static final int REQUEST_CODE=0X222;//裁切请求码
    private Uri sourceUri;//图片源
    private String sourcePath;//图片源路径
    private ArrayList<String> sourcePathList;//图片源路径集合
    private int aspX=1;
    private int aspY=1;
    private static File desfile;//输出文件
    private boolean isCompress=false;//是否压缩

    private void setSourceUri(Uri sourceUri) {
        this.sourceUri = sourceUri;
    }

    private void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    private void setAspX(int aspX) {
        this.aspX = aspX;
    }


    private void setAspY(int aspY) {
        this.aspY = aspY;
    }

    private void setCompress(boolean compress) {
        isCompress = compress;
    }

    private void setSourcePathList(ArrayList<String> sourcePathList) {
        this.sourcePathList = sourcePathList;
    }

    /**
     * 调用系统剪切
     * @param
     */
   /* public void crop(Activity activity){
        desfile = new File(activity.getExternalCacheDir(), System.currentTimeMillis()    + ".jpg");
        Uri cropImageUri=Uri.fromFile(desfile);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if(sourcePath!=null&&!sourcePath.isEmpty()){
            sourceUri= getImageContentUri(sourcePath,activity);
        }
        if(sourceUri!=null) {
            intent.setDataAndType(sourceUri, "image");
        }
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspX);
        intent.putExtra("aspectY", aspY);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri);
        activity.startActivityForResult(intent, CropUtil.REQUEST_CODE);
    }*/

    /**
     * 剪切回调
     * @param onCropResultListener
     * @return
     */
   public CropUtil setCropListener(OnCropResultListener onCropResultListener){
       CropActivity.onCropResultListener=onCropResultListener;
       return this;
   }


    /**
     * 自定义剪切
     * @param activity
     */
    public void crop(Activity activity){
        CropActivity.isCompress=isCompress;
        Intent intent=new Intent();
        intent.setClass(activity,CropActivity.class);
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("paths",sourcePathList);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public static File onSuccess(){
        return desfile;
    }

    public static class CropBuilder{
        private Uri sourceUri;//图片源
        private String sourcePath;//图片源路径
        private int aspX=1;
        private int aspY=1;
        private ArrayList<String> sourcePathList;
        private boolean isCompress=false;//是否压缩

        public CropBuilder setSourcePath(String sourcePath) {
            this.sourcePath = sourcePath;
            return this;
        }

        public CropBuilder setAspX(int aspX){
            this.aspX=aspX;
            return  this;
        }

        public CropBuilder setAspY(int aspY){
            this.aspY=aspY;
            return this;
        }

        public CropBuilder setSourceUri(Uri uri){
            this.sourceUri=uri;
            return this;
        }

        public CropBuilder setSourcePathList(ArrayList<String> sourcePathList) {
            this.sourcePathList = sourcePathList;
            return this;
        }

        public CropBuilder setCompress(boolean compress) {
            isCompress = compress;
            return this;
        }

        public CropUtil create(){
            CropUtil cropUtil=new CropUtil();
            cropUtil.setAspX(aspX);
            cropUtil.setAspY(aspY);
            cropUtil.setSourcePath(sourcePath);
            cropUtil.setSourceUri(sourceUri);
            cropUtil.setSourcePathList(sourcePathList);
            cropUtil.setCompress(isCompress);
            return cropUtil;
        }
    }



    /**
     * 获取选择本地相册图片的uri
     * @param path
     * @return
     */
    private Uri getImageContentUri(String path,Activity activity){
        Cursor cursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{path}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, ""+id);
        }else {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, path);
            return activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }
    }


    /**
     * 剪切结果回调
     */
    public interface OnCropResultListener{
        void onSuccess(ArrayList<String> paths);
        void onError(Throwable e);
    }
}
