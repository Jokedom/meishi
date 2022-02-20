package com.zero.du.meishi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xuexiang.xui.utils.SnackbarUtils;
import com.zero.du.meishi.bean.User;
import com.zero.du.meishi.databinding.ActivityLoginbgBinding;

public class LoginActivity extends AppCompatActivity {
    private User userbean;
    private LoginViewModel loginViewModel;
    public static final int REQUEST_CODE_REGISTER = 1;
    private ActivityLoginbgBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loginbg);
        binding.setLogin(loginViewModel);
        // 进行数据观察
        binding.setLifecycleOwner(this);
        setStatusTextColor(true);
        //用来沉浸状态栏
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        //得到当前界面的装饰视图
        if(Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //设置让应用主题内容占据状态栏和导航栏
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏和导航栏颜色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }

        binding.login.setOnClickListener((v)->{
            login(v);
        });
        binding.textView47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, REQUEST_CODE_REGISTER);
            }
        });
    }
    public void login(View v){

        if(loginViewModel.user.get()!=""&&loginViewModel.pwd.get()!=""){
            //通过viewmodel 查找用户名 拿到数据库信息
            loginViewModel.getUserbean(loginViewModel.user.get()).observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if(user!=null){
                        //数据库用户名进行与前台数据进行对比
                        if(loginViewModel.user.get().equals(user.user)&&loginViewModel.pwd.get().equals(user.pwd)){

                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE); //私有数据
                            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                            editor.putString("id", user.user);
                            editor.putString("phone", user.phone);
                            editor.putString("pwd", user.pwd);
                            editor.putString("status",user.status+"");
                            editor.commit();//提交修改
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {

                            SnackbarUtils.Short(v, "用户名或密码错误").show();
                        }
                    }else {
                        SnackbarUtils.Short(v, "用户不存在").show();

                    }
                }
            });

        }else{
            SnackbarUtils.Short(v, "用户名或密码不能为空").show();

        }

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REGISTER && resultCode == RegisterActivity.RESULT_CODE_REGISTER && data != null) {
            Bundle extras = data.getExtras();
            String account = extras.getString("username", "");
            String password = extras.getString("password", "");
            loginViewModel.user.set(account);
            loginViewModel.pwd.set(password);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}