package com.zero.du.meishi;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.GsonBuilder;
import com.zero.du.meishi.bean.Menu;
import com.zero.du.meishi.databinding.ActivityMainBinding;
import com.zero.du.meishi.databinding.HomeImageitemBinding;
import com.zero.du.meishi.util.MiuiUtil;
import com.zero.du.meishi.util.RecyclerViewAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        setStatusTextColor(true);
        SharedPreferences share=getSharedPreferences("user", Activity.MODE_WORLD_READABLE);
        String user = share.getString("id", "");
        String status = share.getString("status", "");
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_jishi,R.id.navigation_meishiadmin, R.id.navigation_mine)
                .build();
        if(status.equals("1")){
            binding.navView.getMenu().findItem(R.id.navigation_home).setVisible(false);
            binding.navView.getMenu().findItem(R.id.navigation_jishi).setVisible(false);

            binding.navView.getMenu().findItem(R.id.navigation_meishiadmin).setVisible(true);
            navController.navigate(R.id.navigation_meishiadmin);

        }else {
            binding.navView.getMenu().findItem(R.id.navigation_home).setVisible(true);
            binding.navView.getMenu().findItem(R.id.navigation_jishi).setVisible(true);
            binding.navView.getMenu().findItem(R.id.navigation_meishiadmin).setVisible(false);
        }


//        new RecyclerViewAdapter<>()

        binding.navView.setVisibility(View.VISIBLE);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    protected void setStatusTextColor(boolean isDark) {
        if (isDark) {
            //黑色字体
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } else {
            //白色字体
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }


    }
}