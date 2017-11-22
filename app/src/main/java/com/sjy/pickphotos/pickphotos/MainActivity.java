package com.sjy.pickphotos.pickphotos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                PickPhotos.Album(MainActivity.this)
                        .setMultiChooseSize(6)
                        .setIsCompress(true)
                        .setIsCrop(false)
                        .setOnResultListener(new OnResultListener() {

                            @Override
                            public void onSucess(ArrayList<String> imagePathList) {
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
