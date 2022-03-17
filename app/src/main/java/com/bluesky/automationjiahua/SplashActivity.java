package com.bluesky.automationjiahua;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.EnvironmentCompat;

import com.bluesky.automationjiahua.base.App;
import com.bluesky.automationjiahua.databinding.ActivitySplashBinding;
import com.bluesky.automationjiahua.utils.AssetsCopyUtils;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.io.File;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        //requestPermissions();
        //initData();
        binding.btnEnter.setVisibility(View.VISIBLE);

    }

    private void initView() {
        binding.btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initData() {
        String path_huachan = getDatabasePath(App.HUACHAN_DEVICE_DATA_BASE_NAME).getAbsolutePath();
        toast("数据库目录=" + path_huachan);
        Log.e("复制数据库文件,目录=", path_huachan);
//        String path_interlock = getDatabasePath(App.INTER_LOCK_DATA_BASE_NAME).getAbsolutePath();

        AssetsCopyUtils.copyAssetsFile2Phone(this, App.HUACHAN_DEVICE_DATA_BASE_NAME, path_huachan);
//        AssetsCopyUtils.copyAssetsFile2Phone(this, App.INTER_LOCK_DATA_BASE_NAME, path_interlock);

        binding.btnEnter.setVisibility(View.VISIBLE);
    }

    private void requestPermissions() {
        XXPermissions.with(this)
                // 申请安装包权限
                //.permission(Permission.REQUEST_INSTALL_PACKAGES)
                // 申请悬浮窗权限
                //.permission(Permission.SYSTEM_ALERT_WINDOW)
                // 申请通知栏权限
                //.permission(Permission.NOTIFICATION_SERVICE)
                // 申请系统设置权限
                //.permission(Permission.WRITE_SETTINGS)
                // 申请单个权限
                //.permission(Permission.RECORD_AUDIO)
                // 申请多个权限
                //.permission(Permission.Group.CALENDAR)
                .permission(Permission.Group.STORAGE)
                .permission(Permission.CALL_PHONE)
                //.permission(Permission.MANAGE_EXTERNAL_STORAGE)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            //toast("获取外置存储卡读写权限成功");
                            Snackbar.make(binding.getRoot(), "获取外置存储卡读写权限成功", BaseTransientBottomBar.LENGTH_SHORT).show();
                            initData();

                        } else {
                            //toast("获取部分权限成功，但部分权限未正常授予");
                            Snackbar.make(binding.getRoot(), "获取部分权限成功，但部分权限未正常授予", BaseTransientBottomBar.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            //toast("被永久拒绝授权，请手动授予外置存储卡读写权限");
                            Snackbar.make(binding.getRoot(), "被永久拒绝授权，请手动授予外置存储卡读写权限", BaseTransientBottomBar.LENGTH_SHORT).show();

                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(SplashActivity.this, permissions);
                        } else {
                            //toast("获取外置存储卡读写权限失败");
                            Snackbar.make(binding.getRoot(), "获取外置存储卡读写权限失败", BaseTransientBottomBar.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

}