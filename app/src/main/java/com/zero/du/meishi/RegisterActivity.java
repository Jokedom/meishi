package com.zero.du.meishi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xuexiang.xui.utils.SnackbarUtils;
import com.zero.du.meishi.bean.User;
import com.zero.du.meishi.databinding.ActivityRegisterBinding;
import com.zero.du.meishi.repository.UserRepository;

public class RegisterActivity extends AppCompatActivity {
    public static final int RESULT_CODE_REGISTER = 0;
    private ActivityRegisterBinding binding;
    private RegisterViewModel zhuceViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        zhuceViewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(this.getApplication())).get(RegisterViewModel.class);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        binding.setRegister(zhuceViewModel);
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());
        binding.zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd = zhuceViewModel.pwd.get();
                String pwd2 = zhuceViewModel.pwd2.get();
                zhuceViewModel.query(zhuceViewModel.username.get()).observe(RegisterActivity.this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        if(user==null){
                            if(pwd.equals(pwd2)){
                                UserRepository userRepository = new UserRepository(RegisterActivity.this);
                                User user2 = new User(zhuceViewModel.username.get(),zhuceViewModel.pwd.get(),zhuceViewModel.phone.get(),0);
                                userRepository.insertUser(user2);
                                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putString("username",zhuceViewModel.username.get());
                                bundle.putString("password",zhuceViewModel.pwd.get());
                                intent.putExtras(bundle);
                                setResult(RESULT_CODE_REGISTER,intent);
                                finish();
                            }else {
                                SnackbarUtils.Short(view, "两次密码不一样").show();

                            }
                        }
                    }
                });

            }
        });
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