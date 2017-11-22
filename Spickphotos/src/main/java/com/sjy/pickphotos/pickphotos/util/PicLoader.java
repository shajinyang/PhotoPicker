package com.sjy.pickphotos.pickphotos.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.sjy.pickphotos.pickphotos.R;
import com.sjy.pickphotos.pickphotos.ui.PicBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 图片获取类（获取本地图片信息）
 * Created by sjy on 2017/11/18.
 */

public class PicLoader {
    private Context mContext;
    public static final String MAP_KEY="全部图片";
    public PicLoader(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 获取本地图片
     */
    public HashMap<String, List<PicBean>>  getPics() {
        HashMap<String, List<PicBean>> map = new HashMap<>();//存储相册
        List<PicBean> listall=new ArrayList<PicBean>();//存储全部图片
        //添加拍照标识
        PicBean picBeantakephoto=new PicBean();
        picBeantakephoto.setDisplayname(MAP_KEY);
        picBeantakephoto.setThumbnailpath(R.mipmap.album_ic_image_camera_white+"");
        listall.add(picBeantakephoto);
        map.put(MAP_KEY,listall);
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projImage = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,//路径
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,//文件夹名称
                MediaStore.Images.Media.SIZE
        };
        ContentResolver mCursor = mContext.getContentResolver();
        Cursor cursor = mCursor.query(
                mImageUri,
                projImage,
                null,
                null,
                MediaStore.Images.Media.DATE_MODIFIED+" desc");//按日期倒序
        if (cursor.moveToFirst()) {

            do {
                PicBean picBean = new PicBean();
                picBean.setId(cursor.getString(0));
                picBean.setThumbnailpath(cursor.getString(1));
                picBean.setDisplayname(cursor.getString(2));
                picBean.setSize(cursor.getString(3));
                listall.add(picBean);
                if(map.containsKey(cursor.getString(2))){
                    List<PicBean> list=map.get(cursor.getString(2));
                    list.add(picBean);
                }else {

                    List<PicBean> list=new ArrayList<>();
                    //添加拍照标识
                    PicBean picBeantop = new PicBean();
                    picBeantop.setThumbnailpath(R.mipmap.album_ic_image_camera_white+"");
                    picBeantop.setDisplayname(cursor.getString(2));
                    list.add(picBeantop);

                    list.add(picBean);
                    map.put(cursor.getString(2),list);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return map;
    }
}
