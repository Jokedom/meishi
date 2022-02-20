package com.zero.du.meishi.ui.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.zero.du.meishi.LoginActivity;
import com.zero.du.meishi.MainActivity;
import com.zero.du.meishi.R;
import com.zero.du.meishi.databinding.FragmentMineBinding;

import org.jetbrains.annotations.NotNull;

import static android.content.Context.MODE_PRIVATE;


public class MineFragment extends Fragment {

    private MineViewModel mineViewModel;
    private FragmentMineBinding binding;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mineViewModel =
                new ViewModelProvider(this).get(MineViewModel.class);

        binding = FragmentMineBinding.inflate(inflater, container, false);
        binding.setMine(mineViewModel);
        SharedPreferences share=getActivity().getSharedPreferences("user", MODE_PRIVATE);
        String user = share.getString("id", "");
        String phone = share.getString("phone", "");
        Log.d("ssssss",user);
        binding.textView39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController(getView());

                navController.navigate(R.id.navigation_shoucang);
            }
        });
        binding.textView45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController(getView());

                navController.navigate(R.id.navigation_xiugaimima);
            }
        });
        binding.btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new AlertDialog.Builder(getActivity())
                        .setTitle("你确定要退出登录吗")
                        .setMessage("确定吗")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
            }
        });
        binding.textView41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navController = Navigation.findNavController(getView());

                navController.navigate(R.id.navigation_about);

            }
        });
        mineViewModel.username.set(user);
        mineViewModel.phone.set(phone);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}