package com.zero.du.meishi.ui.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xuexiang.xui.utils.SnackbarUtils;
import com.zero.du.meishi.LoginActivity;
import com.zero.du.meishi.MainActivity;
import com.zero.du.meishi.R;
import com.zero.du.meishi.bean.User;
import com.zero.du.meishi.databinding.FragmentSearchdetailBinding;
import com.zero.du.meishi.databinding.FragmentchangpwdBinding;

import static android.content.Context.MODE_PRIVATE;

public class ChangePwdFragment extends Fragment {

    private MineViewModel mineViewModel;
    private FragmentchangpwdBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mineViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(MineViewModel.class);
        binding = FragmentchangpwdBinding.inflate(inflater, container, false);
        BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
        viewById.setVisibility(View.GONE);
        binding.setChpwd(mineViewModel);
        binding.setLifecycleOwner(this);
        binding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(view);
            }
        });
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    public void change(View view) {

//        LiveData<User> userLiveData = repository.queryUser(user.get());
        SharedPreferences share = getActivity().getSharedPreferences("user", MODE_PRIVATE);

        String user22 = share.getString("id", "");
        String pwd = share.getString("pwd", "");


        if (!mineViewModel.oldpwd.get().equals("") && !mineViewModel.newpwd.get().equals("") && !mineViewModel.newpwd2.get().equals("")) {


                        if(mineViewModel.oldpwd.get().equals(pwd)){


                            if (mineViewModel.newpwd.get().equals(mineViewModel.newpwd2.get())){
                              mineViewModel.loginuser(mineViewModel.newpwd.get(),user22);

                                SharedPreferences.Editor editor = share.edit();//获取编辑器

                                editor.putString("pwd", mineViewModel.oldpwd.get());

                                editor.commit();//提交修改
                                SnackbarUtils.Short(view, "修改成功").show();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                getActivity().startActivity(intent);
//                                Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
                            }else {
                                SnackbarUtils.Short(view, "两次密码不一样").show();
                            }
                        }else {
                                SnackbarUtils.Short(view, "旧密码不对").show();
                        }

        } else {
            SnackbarUtils.Short(view, "必填项不能为空").show();

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
        BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
        viewById.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }
}