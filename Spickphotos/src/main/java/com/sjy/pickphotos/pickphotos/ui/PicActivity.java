package com.sjy.pickphotos.pickphotos.ui;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjy.pickphotos.pickphotos.BuildConfig;
import com.sjy.pickphotos.pickphotos.takephoto.TakePhotoUtil;
import com.sjy.pickphotos.pickphotos.util.PicLoader;
import com.sjy.pickphotos.pickphotos.R;
import com.sjy.pickphotos.pickphotos.compress.CompressUtil;
import com.sjy.pickphotos.pickphotos.crop.CropUtil;
import com.sjy.pickphotos.pickphotos.listeners.OnResultListener;
import com.sjy.pickphotos.pickphotos.permissions.IpermissionCallBackListener;
import com.sjy.pickphotos.pickphotos.permissions.PermissionRequester;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 画廊
 * Created by sjy on 2017/11/18.
 */

public class PicActivity extends AppCompatActivity  {
     public static OnResultListener onResultListener;
     public static int multiChooseSize=1;//图片选择最大张数
     public static boolean isCompress=false;//是否压缩
     public static boolean isCrop=false;//是否裁剪
     public static int exitIco=R.drawable.ic_left_arrow_back;//返回图标
     public static String toolBarColor="#666666";//toolbar 颜色
     public static String stateBarColor="#666666";//状态栏颜色

     AlertDialog alertDialog;//文件夹封面弹框
     Map<String,List<PicBean>> list;//所有图片包含文件夹
     List<PicBean> listall;//全部图片
     List<CoverBean> listcovers;//封面列表集合
     RecyclerView recyclerView;
     File desfile;
     int totalChooseCount=0;//当前已选图片数量
     ArrayList<String> responsePaths=new ArrayList<>();//返回路径集合

    private int cropIndex=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.choosepic_main_activity);
        ((ImageView)findViewById(R.id.back)).setImageResource(exitIco);
        findViewById(R.id.toolbar).setBackgroundColor(Color.parseColor(toolBarColor));
        findViewById(R.id.state_bar).setBackgroundColor(Color.parseColor(stateBarColor));
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertPopWindow();
            }
        });
        bindPicData(PicLoader.MAP_KEY);
        ((TextView)findViewById(R.id.choose_count)).setText("已选"+totalChooseCount+"张");
        findViewById(R.id.choose_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responsePics();
            }
        });
        findViewById(R.id.make_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responsePics();
            }
        });
    }


    /**
     * 处理图片并返回所选择的图片路径
     */
    private void responsePics(){
        if (onResultListener != null) {
            //不压缩，不裁剪
            if(!isCompress&&!isCrop){
                onResultListener.onSucess(responsePaths);
            }
            //压缩不裁剪
            else if(isCompress&&!isCrop){
                final ArrayList<String> newlist=new ArrayList<>();
                final int[] i = {0};
                if(responsePaths.size()>0){
                    Snackbar.make(findViewById(R.id.toolbar),"正在处理图片",Snackbar.LENGTH_INDEFINITE).show();
                }
                for (String path:responsePaths
                     ) {
                    CompressUtil.compress(new CompressUtil.OnCompressListener() {
                        @Override
                        public void onStart() {

                        }
                        @Override
                        public void onSuccess(File file) {
                            i[0]++;
                            newlist.add(file.getAbsolutePath());
                            if(i[0]==responsePaths.size()){
                                onResultListener.onSucess(newlist);
                                finish();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            i[0]++;
                            if(i[0]==responsePaths.size()){
                                onResultListener.onSucess(newlist);
                                finish();
                            }
                        }
                    },path,PicActivity.this);
                }
            }
            //裁剪不压缩
            else if(!isCompress&&isCrop){
                if(responsePaths.size()>0) {
                    new CropUtil.CropBuilder()
                            .setSourcePathList(responsePaths)
                            .create()
                            .setCropListener(new CropUtil.OnCropResultListener() {
                                @Override
                                public void onSuccess(ArrayList<String> paths) {
                                    onResultListener.onSucess(paths);
                                    finish();
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            })
                            .crop(PicActivity.this);
                }
            }
            //压缩裁剪
            else if(isCompress&&isCrop){
                if(responsePaths.size()>0) {
                    new CropUtil.CropBuilder()
                            .setSourcePathList(responsePaths)
                            .setCompress(true)
                            .create()
                            .setCropListener(new CropUtil.OnCropResultListener() {
                                @Override
                                public void onSuccess(ArrayList<String> paths) {
                                    onResultListener.onSucess(paths);
                                    finish();
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            })
                            .crop(PicActivity.this);
                }
            }

        }

    }


    /**
     * 绑定画廊数据
     * @param key
     */
    private void bindPicData(String key){
        PicLoader picLoader=new PicLoader(this);
        list= picLoader.getPics();
        listall=list.get(key);
        recyclerView= findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(PicActivity.this,3));
        PermissionRequester.getInstance()
                .requestPermissions(new IpermissionCallBackListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        recyclerView.setAdapter(new CommonAdapter<PicBean>(PicActivity.this,R.layout.pick_photo_recycleview_img,listall) {
                            @Override
                            public void convert(final BaseViewHolder holder, final PicBean picBean, int position) {
                                if(position==0){
                                    holder.setVisibility(R.id.sel,View.INVISIBLE)
                                            .setImageResourceId(R.id.img,Integer.parseInt(picBean.getThumbnailpath()))
                                            .setOnClickListener(R.id.img, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    TakePhotoUtil
                                                           .camera(PicActivity.this);
                                                }
                                            });
                                }else {
                                    holder.setVisibility(R.id.sel,View.VISIBLE)
                                            .setImageResource(R.id.img, picBean.getThumbnailpath())
                                            .setOnClickListener(R.id.img, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            });
                                    (holder.getView(R.id.sel)).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(totalChooseCount<multiChooseSize||picBean.isSel()) {
                                                picBean.setSel(!picBean.isSel());
                                                if (picBean.isSel()) {
                                                    totalChooseCount++;
                                                    responsePaths.add(picBean.getThumbnailpath());
                                                } else {
                                                    responsePaths.remove(picBean.getThumbnailpath());
                                                    totalChooseCount--;
                                                }
                                                ((TextView) findViewById(R.id.choose_count)).setText("已选" + totalChooseCount + "张");
                                            }else  {
                                                Snackbar.make(findViewById(R.id.toolbar),"最多可选"+multiChooseSize+"张图片",Snackbar.LENGTH_SHORT).show();
                                                ((AppCompatCheckBox)v).setChecked(false);
                                            }
                                        }
                                    });
                                    ((AppCompatCheckBox)holder.getView(R.id.sel)).setChecked(picBean.isSel());
                                }
                            }
                        });
                    }
                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {

                    }
                },this,2514, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==TakePhotoUtil.REQUEST_CAMERA){//拍照
            File file=TakePhotoUtil.onSuccess();
            responsePaths.clear();
            responsePaths.add(file.getAbsolutePath());
            responsePics();
        }
    }




    /**
     * 封面弹框逻辑
     */
    private void  alertPopWindow(){
        if(alertDialog!=null&&alertDialog.isShowing()){
            alertDialog.dismiss();
            return;
        }
        if(alertDialog==null) {
           AlertDialog.Builder builder=new AlertDialog.Builder(PicActivity.this);
            View contentView= LayoutInflater.from(PicActivity.this).inflate(R.layout.pick_photo_popwindow_alert,null);
            RecyclerView recyclerView=contentView.findViewById(R.id.alert_recycleview);
            bindCoverData(recyclerView);
            alertDialog= builder.setView(contentView)
                    .create();
        }
        alertDialog.show();
    }


    /**
     * 绑定封面数据
     * @param mrecyclerView
     */
    private void bindCoverData(RecyclerView mrecyclerView){
        listcovers=new ArrayList<>();
        Iterator<String> iterator= list.keySet().iterator();
        while (iterator.hasNext()){
            String key= iterator.next();
            List<PicBean> conlist= list.get(key);
            if(conlist.size()>1){
                CoverBean coverBean=new CoverBean();
                coverBean.setCoverName(key);
                coverBean.setNum(conlist.size()-1+"张");
                coverBean.setCoverPic(conlist.get(1).getThumbnailpath());
                listcovers.add(coverBean);
            }
        }
        mrecyclerView.setLayoutManager(new LinearLayoutManager(PicActivity.this));
        mrecyclerView.setAdapter(new CommonAdapter<CoverBean>(PicActivity.this,R.layout.pick_photo_recycleview_cover_item,listcovers) {
            @Override
            public void convert(BaseViewHolder holder, final CoverBean picBean, int position) {

                holder.setImageResource(R.id.pick_pic_cover_img,picBean.getCoverPic())
                        .setText(R.id.pick_pic_cover_title,picBean.getCoverName())
                .setText(R.id.pick_pic_cover_nums,picBean.getNum())
                .setOnClickListener(R.id.parent, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bindPicData(picBean.getCoverName());
                        ((TextView)findViewById(R.id.title)).setText(picBean.getCoverName());
                        alertPopWindow();
                    }
                });
            }
        });
    }


}
