package com.sjy.pickphotos.pickphotos.permissions;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

/**
 * 权限请求类 single instance
 * Created by sjy on 2017/5/8.
 */

public class PermissionRequester {

    private static PermissionRequester instance;
    public static PermissionRequester getInstance(){
        if(instance==null){
            synchronized (PermissionRequester.class){
                if(instance==null){
                    instance=new PermissionRequester();
                }
            }
        }
        return instance;
    }

    /**
     * 请求权限
     * @param context 上下文
     * @param requestCode 请求码
     * @param permissions 权限集
     */
    public void requestPermissions(IpermissionCallBackListener ipermissionCallBackListener, Context context, int requestCode, String... permissions){
        AndPermission.with(context)
                .requestCode(requestCode)
                .permission(permissions)
                .callback(ipermissionCallBackListener)
                .start();
    }
    /**
     * 请求权限
     * @param activity activity (当用户第一次拒绝后，可以弹出提示框)
     * @param requestCode 请求码
     * @param permissions 权限集
     */
    public void requestPermissions(IpermissionCallBackListener ipermissionCallBackListener, final Activity activity, int requestCode, String... permissions){
        AndPermission.with(activity)
                .requestCode(requestCode)
                .permission(permissions)
                .callback(ipermissionCallBackListener)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        AndPermission.rationaleDialog(activity, rationale).show();
                    }
                })
                .start();
    }
    /**
     * 请求权限
     * @param fragment fragment(当用户第一次拒绝后，可以弹出提示框)
     * @param requestCode 请求码
     * @param permissions 权限集
     */
    public void requestPermissions(IpermissionCallBackListener ipermissionCallBackListener, final Fragment fragment, int requestCode, String... permissions){
        AndPermission.with(fragment)
                .requestCode(requestCode)
                .permission(permissions)
                .callback(ipermissionCallBackListener)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        AndPermission.rationaleDialog(fragment.getActivity(), rationale).show();
                    }
                })
                .start();
    }

}
