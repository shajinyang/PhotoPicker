package com.sjy.pickphotos.pickphotos;

import android.content.Context;

import com.sjy.pickphotos.pickphotos.business.PicPresenter;
import com.sjy.pickphotos.pickphotos.util.SConstants;

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
        SConstants.setmContext(mContext);
        return new PicPresenter(mContext);
    }



}
