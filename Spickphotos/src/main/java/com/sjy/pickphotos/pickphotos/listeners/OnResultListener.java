package com.sjy.pickphotos.pickphotos.listeners;

import java.util.ArrayList;

/**
 * 图片选择监听
 * Created by sjy on 2017/11/20.
 */

public interface OnResultListener {
    void onSucess(ArrayList<String> imagePathList);
    void onCancel();
}
