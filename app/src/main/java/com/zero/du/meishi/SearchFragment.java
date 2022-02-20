package com.zero.du.meishi;

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
import androidx.fragment.app.FragmentActivity;
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
import com.google.gson.reflect.TypeToken;
import com.xuexiang.xui.utils.Utils;
import com.zero.du.meishi.bean.Menu;
import com.zero.du.meishi.bean.Search;
import com.zero.du.meishi.bean.Shipu;

import com.zero.du.meishi.dao.ShipuDao;
import com.zero.du.meishi.databinding.AdapterSearchRecordTagItemBinding;
import com.zero.du.meishi.databinding.FragmentSerachBinding;
import com.zero.du.meishi.repository.ShipuRepository;
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

public class SearchFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentSerachBinding binding;
    private   Menu menu;
    private RecyclerViewAdapter<Search> recyclerViewAdapter;
    private NavController navController;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentSerachBinding.inflate(inflater, container, false);
        MainActivity activity = (MainActivity)getActivity();
        BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
        viewById.setVisibility(View.GONE);
//        activity.getSupportActionBar().hide();
        binding.recyclerView2.setLayoutManager(getFlexboxLayoutManager(getContext()));
//        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerViewAdapter = new RecyclerViewAdapter<Search>(R.layout.adapter_search_record_tag_item) {
            @Override
            public void convert(MyViewHolder holder, int p) {
                holder.itemView.setTag(tlist.get(p));

                //使用databinding 进行 数据项的装配
                 AdapterSearchRecordTagItemBinding adapterSearchRecordTagItemBinding= (AdapterSearchRecordTagItemBinding) holder.getBinding();
                adapterSearchRecordTagItemBinding.setSe(tlist.get(p));

            }

        };
        List<Search> searches= new ArrayList<>();


            searches.add(new Search("烘焙入门"));
        searches.add(new Search("面食点心"));
        searches.add(new Search("家常小炒"));
        searches.add(new Search("健康饮食"));
        searches.add(new Search("甜点小吃"));


        binding.recyclerView2.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setTlist(searches);
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener<Search>() {
            @Override
            public void onItemClick(View view, Search data) {
                navController = Navigation.findNavController(getView());
                Bundle bundle = new Bundle();
                bundle.putString("name",data.remen);

                navController.navigate(R.id.navigation_searchdetail,bundle);
            }
        });
//        new Qidong(recyclerViewAdapter,searches).execute("");
        View root = binding.getRoot();
        setHasOptionsMenu(true);
        return root;
    }
//

    public  <T> T fromToJson(String json,Type listType){
        Gson gson = new Gson();
        T t = null;
        t = gson.fromJson(json,listType);
        return t;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull android.view.Menu menu, @NonNull @NotNull MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.lookup);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        if(searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
//
                    Log.d("sssss1",query);
                    navController = Navigation.findNavController(getView());
                    Bundle bundle = new Bundle();
                    bundle.putString("data",query);
                    navController.navigate(R.id.navigation_searchdetail,bundle);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
    }

    public static FlexboxLayoutManager getFlexboxLayoutManager(Context context) {
        //设置布局管理器
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(context);
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal:
        // 主轴为水平方向，起点在左端。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列:
        // 按正常方向换行
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        //justifyContent 属性定义了项目在主轴上的对齐方式:
        // 交叉轴的起点对齐
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        return flexboxLayoutManager;
    }
    class Qidong extends AsyncTask<String,Void,Void> {

        private RecyclerViewAdapter<Search> recyclerViewAdapter;
        List<Search> list;
        public Qidong(RecyclerViewAdapter<Search> recyclerViewAdapter,List<Search> list){
            this.list=list;
            this.recyclerViewAdapter=recyclerViewAdapter;
        }


        @Override
        protected Void doInBackground(String... strings) {
            recyclerViewAdapter.setTlist(list);
           recyclerViewAdapter.notifyDataSetChanged();
            return null;
        }
    }
    private String getLocalJson(String name) {
        StringBuilder newstringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = getResources().getAssets().open(name);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                newstringBuilder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newstringBuilder.toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
        viewById.setVisibility(View.VISIBLE);
    }

}