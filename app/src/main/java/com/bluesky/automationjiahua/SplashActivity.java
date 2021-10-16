package com.bluesky.automationjiahua;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bluesky.automationjiahua.base.App;
import com.bluesky.atuomationjiahua.databinding.ActivitySplashBinding;
import com.bluesky.automationjiahua.utils.AssetsCopyUtils;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

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
        initData();
        requestPermissions();
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
        String path = getDatabasePath(App.DATA_BASE_NAME).getAbsolutePath();
        AssetsCopyUtils.copyAssetsFile2Phone(this, App.DATA_BASE_NAME, path);
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
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            toast("获取外置存储卡读写权限成功");
                        } else {
                            toast("获取部分权限成功，但部分权限未正常授予");
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            toast("被永久拒绝授权，请手动授予外置存储卡读写权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(SplashActivity.this, permissions);
                        } else {
                            toast("获取外置存储卡读写权限失败");
                        }
                    }
                });
    }

    private void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

}