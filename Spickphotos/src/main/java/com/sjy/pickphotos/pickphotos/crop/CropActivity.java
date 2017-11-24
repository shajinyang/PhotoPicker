package com.sjy.pickphotos.pickphotos.crop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sjy.pickphotos.pickphotos.R;
import com.sjy.pickphotos.pickphotos.crop.util.FileUtil;
import com.sjy.pickphotos.pickphotos.crop.view.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.Luban;

/**
 * 剪切UI
 * Created by sjy on 2017/11/23.
 */

public class CropActivity extends AppCompatActivity {
    public static CropUtil.OnCropResultListener onCropResultListener;
    public static boolean isCompress=false;//是否压缩
    ArrayList<String> pathList;
    ArrayList<String> newCropPathList=new ArrayList<>();
    CropImageView appCompatImageView;
    int position=0;
    int cropPosition=0;
    boolean isCropOver=false;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.pick_photo_crop_activity);
        initViews();
        getBundleData();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        findViewById(R.id.make_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position<pathList.size()-1){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            saveCropImg();
                        }
                    }).start();
                    position++;
                    bindImage();
                }else if(position==pathList.size()-1) {
                    Snackbar.make(appCompatImageView,"正在批量裁剪图片",Snackbar.LENGTH_INDEFINITE).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            saveCropImg();
                        }
                    }).start();
                }
            }
        });
    }

    /**
     * 保存裁剪后的图片至本地
     */
    private void saveCropImg(){
        Bitmap bitmap=appCompatImageView.getCroppedImage();
        String cropPath= FileUtil.savePic(bitmap,getExternalCacheDir().getAbsolutePath(),true,CropActivity.this);
        if(isCompress) {
            try {
                List<File> presslist = Luban.with(CropActivity.this).load(cropPath).get();
                if (presslist.size() > 0) {
                    cropPath = presslist.get(0).getAbsolutePath();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        newCropPathList.add(cropPath);
        handler.sendMessage(new Message());
    }

    private void initViews(){
        appCompatImageView=findViewById(R.id.crop_image_view);
    }

    private void bindImage(){
        if(pathList==null||pathList.size()==0||pathList.size()<position+1)return;
        Bitmap bitmap= BitmapFactory.decodeFile(pathList.get(position));
        appCompatImageView.setImageBitmap(bitmap);

    }

    private void  getBundleData(){
        if(getIntent().getExtras()!=null){
            pathList= (ArrayList<String>) getIntent().getExtras().get("paths");
            bindImage();
        }
    }


    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(cropPosition==pathList.size()-1){
                isCropOver=true;
                if(onCropResultListener!=null){
                    onCropResultListener.onSuccess(newCropPathList);
                }
                finish();
            }else {
                cropPosition++;
            }
            return false;
        }
    });
}
