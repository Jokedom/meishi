package com.zero.du.meishi.ui.home;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zero.du.meishi.MainActivity;
import com.zero.du.meishi.R;
import com.zero.du.meishi.bean.Menu;
import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.bean.ShouCang;
import com.zero.du.meishi.databinding.FragmentCaipudetailBinding;
import com.zero.du.meishi.databinding.FragmentHomeBinding;
import com.zero.du.meishi.databinding.HomeImageitemBinding;
import com.zero.du.meishi.databinding.HomeShipuitemBinding;
import com.zero.du.meishi.util.RecyclerViewAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class DetailFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentCaipudetailBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentCaipudetailBinding.inflate(inflater, container, false);
        SharedPreferences share=getActivity().getSharedPreferences("user", Activity.MODE_WORLD_READABLE);
        String user = share.getString("id", "");
        BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
        viewById.setVisibility(View.GONE);
        if(getArguments()!=null) {
            long data = getArguments().getLong("data");
            int shi = getArguments().getInt("shi");
            homeViewModel.find(data).observe(getActivity(), new Observer<ShouCang>() {
                @Override
                public void onChanged(ShouCang shouCang) {

                    if (shouCang!=null){
                        if(shouCang.user.equals(user)){
                            binding.imageView6.setImageResource(R.drawable.ic_aixin2);
                        }

                    }
                }
            });
            if(shi==1){
                binding.imageView6.setImageResource(R.drawable.ic_aixin2);
            }
            Log.d("ssssss",data+"");
            homeViewModel.sid.set(data);
        }

        homeViewModel.queryShipu().observe(getActivity(), new Observer<Shipu>() {
            @Override
            public void onChanged(Shipu shipuDetailResult) {
                binding.setShipu(shipuDetailResult);
            }
        });

        binding.setLifecycleOwner(this);


        View root = binding.getRoot();


        return root;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
        viewById.setVisibility(View.VISIBLE);
    }

}