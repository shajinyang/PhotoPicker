package com.sjy.pickphotos.pickphotos.business;

import android.content.Context;
import android.content.Intent;

import com.sjy.pickphotos.pickphotos.listeners.OnResultListener;
import com.sjy.pickphotos.pickphotos.ui.PicActivity;

/**
 * 相册UI业务逻辑
 * Created by sjy on 2017/11/18.
 */

public class PicPresenter {
    private Context mContext;


    public PicPresenter(Context mContext) {
        this.mContext = mContext;
    }


    /**
     * 图片选择结果回调
     * @param onResultListener
     * @return
     */
    public PicPresenter setOnResultListener(OnResultListener onResultListener){
        PicActivity.onResultListener=onResultListener;
        return this;
    }


    /**
     * 选择张数设置
     * @param size
     * @return
     */
    public PicPresenter setMultiChooseSize(int size){
        PicActivity.multiChooseSize=size;
        return this;
    }


    /**
     * 是否压缩
     * @param isCompress
     * @return
     */
    public PicPresenter setIsCompress(boolean isCompress){
        PicActivity.isCompress=isCompress;
        return this;
    }


    /**
     * 是否裁切
     * @param isCrop
     * @return
     */
    public PicPresenter setIsCrop(boolean isCrop){
        PicActivity.isCrop=isCrop;
        return this;
    }

    /**
     * 设置返回图标，默认为白色左箭头
     * @param res
     * @return
     */
    public PicPresenter setExitIco(int res){
        PicActivity.exitIco=res;
        return this;
    }

    /**
     * 设置toolbar背景色
     * @param res "#3f45fe"
     * @return
     */
    public PicPresenter setToolBarColor(String res){
        PicActivity.toolBarColor=res;
        return this;
    }

    /**
     * 设置状态栏颜色
     * @param res "#3f45fe"
     * @return
     */
    public PicPresenter setStateBarColor(String res){
        PicActivity.stateBarColor=res;
        return this;
    }


    public void start(){
        Intent intent=new Intent();
        intent.setClass(mContext, PicActivity.class);
        mContext.startActivity(intent);
    }




}
