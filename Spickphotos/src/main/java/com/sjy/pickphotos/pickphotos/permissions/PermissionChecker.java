package com.sjy.pickphotos.pickphotos.permissions;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * 检查权限类
 * Created by sjy on 2017/5/8.
 */

public class PermissionChecker {
    public Context context;

    public PermissionChecker(Context context){
        this.context=context.getApplicationContext();
    }


    /**
     *检查是否缺少单个权限
     * @return true 已授予权限。false  未授予权限
     */
    public  boolean lackPermission(String permission){
        if(ContextCompat.checkSelfPermission(context,permission)== PackageManager.PERMISSION_GRANTED){
            return  true;
        }else {
            return false;
        }

    }

    /**
     *检查是否缺少多个权限（）
     * @return true 全部授予权限   false 包含未授权权限
     */
    public boolean lackPermissions(String... permissions){
        for (String permission :permissions){
            if(!lackPermission(permission)){
                return false;
            }
        }
        return  true;
    }

}
