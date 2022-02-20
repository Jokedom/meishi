package com.zero.du.meishi.ui.meishi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xuexiang.xui.utils.SnackbarUtils;
import com.zero.du.meishi.R;
import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.databinding.FragmentInsertShipuBinding;
import com.zero.du.meishi.databinding.FragmentJishiBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AddMeishiFragment extends Fragment {



    private FragmentInsertShipuBinding binding;
    private ArrayList<String> rnameitem = new ArrayList<>();
    private ArrayList<String> rmoneyitem = new ArrayList<>();
    private ArrayList<Long> ridtime = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MeiShiViewModel addViewModel  = new ViewModelProvider(getActivity(),new ViewModelProvider.AndroidViewModelFactory(
                getActivity().getApplication())).get(MeiShiViewModel.class);
        BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
        viewById.setVisibility(View.GONE);
        addViewModel.setISCHANGE(false);
        addViewModel.initData();
        binding = FragmentInsertShipuBinding.inflate(inflater, container, false);
        if(getArguments()!=null) {
            Shipu data = (Shipu) getArguments().getSerializable("data");
            addViewModel.setISCHANGE(true);
            addViewModel.dian.set(data.dianzan);
            addViewModel.shoucang.set(data.shoucang);
            addViewModel.sid.set(data.sid);
            addViewModel.shipuname.set(data.name);
            addViewModel.xing.set(String.valueOf(data.xing));
            addViewModel.leibie.set(data.leibie);
            binding.btnCenter.setText("修改食谱");

        }
        addViewModel.selectOption(getActivity());

        binding.setAddvm(addViewModel);
        binding.setLifecycleOwner(this);

//        判断是否存在传输数据
        View root = binding.getRoot();
        setHasOptionsMenu(true);
        return root;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
        BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
        viewById.setVisibility(View.VISIBLE);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }


}