package com.zero.du.meishi;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.zero.du.meishi.bean.Menu;
import com.zero.du.meishi.bean.Search;
import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.databinding.AdapterSearchRecordTagItemBinding;
import com.zero.du.meishi.databinding.FragmentSearchdetailBinding;
import com.zero.du.meishi.databinding.FragmentSerachBinding;
import com.zero.du.meishi.databinding.SearchdetailitemBinding;
import com.zero.du.meishi.ui.home.HomeViewModel;
import com.zero.du.meishi.util.RecyclerViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SearchDetailFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentSearchdetailBinding binding;
    private   Menu menu;
    private RecyclerViewAdapter<Shipu> recyclerViewAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentSearchdetailBinding.inflate(inflater, container, false);
        BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
        viewById.setVisibility(View.GONE);
//        activity.getSupportActionBar().hide();
//        binding.recyclerView2.setLayoutManager(getFlexboxLayoutManager(getContext()));
        binding.recy.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerViewAdapter = new RecyclerViewAdapter<Shipu>(R.layout.searchdetailitem) {
            @Override
            public void convert(MyViewHolder holder, int p) {
                holder.itemView.setTag(tlist.get(p));

                //使用databinding 进行 数据项的装配
                 SearchdetailitemBinding searchdetailitemBinding= (SearchdetailitemBinding) holder.getBinding();
                searchdetailitemBinding.setShipu(tlist.get(p));

            }

        };



        binding.recy.setAdapter(recyclerViewAdapter);
        if(getArguments()!=null){
            String data = getArguments().getString("data");
            String name = getArguments().getString("name");
            if(name==null){
                homeViewModel.getSpDetailByname(data).observe(getActivity(), new Observer<List<Shipu>>() {
                    @Override
                    public void onChanged(List<Shipu> shipuDetailResults) {
                        recyclerViewAdapter.setTlist(shipuDetailResults);
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                });
            }else {
                homeViewModel.getSpDetailByleibie(name).observe(getActivity(), new Observer<List<Shipu>>() {
                    @Override
                    public void onChanged(List<Shipu> shipuDetailResults) {
                        recyclerViewAdapter.setTlist(shipuDetailResults);
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                });
            }

        }

        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                NavController navController = Navigation.findNavController(view);
                Bundle bundle = new Bundle();
                Shipu data1 = (Shipu) data;
                bundle.putSerializable("data",data1.sid);
                navController.navigate(R.id.navigation_shipudetail,bundle);
            }
        });
//        new Qidong(recyclerViewAdapter,searches).execute("");
        View root = binding.getRoot();
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onAttach(@NonNull @NotNull Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull android.view.Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
        viewById.setVisibility(View.VISIBLE);
    }

}