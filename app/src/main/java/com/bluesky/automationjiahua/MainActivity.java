package com.bluesky.automationjiahua;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bluesky.automationjiahua.database.Device;
import com.bluesky.automationjiahua.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

//TODO 1.部分条目的位号中有回车，会造成位号显示不全
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public static List<Device> mDeviceList;
    private long clickTime = 0; // 第一次点击的时间

    private void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        //禁用侧滑打开DrawerLayout
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        binding.appBarMain.fab.setOnClickListener(view -> Snackbar.make(binding.getRoot(), "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", view1 -> {
//                    createDatabase();
//                    fixDeviceFormat();
                }).show());


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_special, R.id.nav_monitor, R.id.nav_interlock, R.id.nav_database)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        //TODO 两次返回退出.
        //1.判断当前fragment是否为main

        /*FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.nav_host_fragment_content_main);
        Fragment fragment=currentFragment.getChildFragmentManager().getPrimaryNavigationFragment();*/


        //侧滑栏打开时,按返回键,关闭侧滑栏,而不是回退Fragment
        DrawerLayout drawer = binding.drawerLayout;
        if (drawer.isOpen()) {
            drawer.closeDrawer(Gravity.LEFT);
        } else {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            NavDestination currentDestination = navController.getCurrentDestination();
            if (currentDestination != null) {
                int id = currentDestination.getId();
                if (id == R.id.nav_home) {
                    Log.e("error,TAG要统一:", "当前fragment是main");

                    if ((System.currentTimeMillis() - clickTime) > 2000) {
                        Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
                        clickTime = System.currentTimeMillis();
                    } else {
                        this.finish();
                    }
                } else {
                    super.onBackPressed();
                }
            }
        }
    }
}