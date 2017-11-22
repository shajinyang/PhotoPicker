package com.sjy.pickphotos.pickphotos;

import android.content.Context;

import com.sjy.pickphotos.pickphotos.business.PicPresenter;

/**
 * 照片选择
 * Created by sjy on 2017/11/18.
 */

public class PhotoPicker {

    /**
     * 相册/拍照
     * @param mContext
     * @return
     */
    public static PicPresenter Album(Context mContext){
        return new PicPresenter(mContext);
    }



}
