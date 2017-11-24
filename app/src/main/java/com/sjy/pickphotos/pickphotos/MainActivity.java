package com.sjy.pickphotos.pickphotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sjy.pickphotos.pickphotos.listeners.OnResultListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPicker.Album(MainActivity.this)
                        .setMultiChooseSize(4)
                        .setIsCompress(true)
                        .setIsCrop(true)
                        .setOnResultListener(new OnResultListener() {

                            @Override
                            public void onSucess(ArrayList<String> imagePathList) {
                                String a="";
                                for (String path:imagePathList
                                     ) {
                                    a+=path+"\n";
                                }
                                Log.e("paths",a);
                            }
                            @Override
                            public void onCancel() {

                            }
                        })
                        .start();
            }
        });

    }


}
