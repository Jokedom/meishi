package com.zero.du.meishi.ui.home;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zero.du.meishi.MainActivity;
import com.zero.du.meishi.R;
import com.zero.du.meishi.bean.Menu;
import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.bean.ShouCang;
import com.zero.du.meishi.databinding.FragmentHomeBinding;
import com.zero.du.meishi.databinding.HomeImageitemBinding;
import com.zero.du.meishi.databinding.HomeShipuitemBinding;
import com.zero.du.meishi.util.RecyclerViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private   Menu menu;
    private RecyclerViewAdapter<Menu> recyclerViewAdapter;
    private int icon[]={
            R.drawable.ic_hongpei,
   R.drawable.ic_mianshi,
      R.drawable.ic_jiachang
        ,R.drawable.ic_jiankang
        , R.drawable.ic_tiandian
    };
    private RecyclerViewAdapter<Shipu> recyclerViewAdapter2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(getActivity(),new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        MainActivity activity = (MainActivity)getActivity();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recyclerView1.setLayoutManager(linearLayoutManager);
        View root = binding.getRoot();
        InputStream inputStream = null;
        binding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);

                navController.navigate(R.id.navigation_search);
            }
        });
        SharedPreferences share=getActivity().getSharedPreferences("user", MODE_PRIVATE);
        String user = share.getString("id", "");
        recyclerViewAdapter = new RecyclerViewAdapter<Menu>(R.layout.home_imageitem) {
            @Override
            public void convert(MyViewHolder holder, int p) {
                holder.itemView.setTag(tlist.get(p));

                //使用databinding 进行 数据项的装配
                HomeImageitemBinding queryuserItemBinding = (HomeImageitemBinding) holder.getBinding();
                queryuserItemBinding.imageView2.setImageResource(icon[p]);
                queryuserItemBinding.setMenu(tlist.get(p));

            }

        };
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener() {

            private NavController navController;

            @Override
            public void onItemClick(View view, Object data) {
                navController = Navigation.findNavController(getView());
                Bundle bundle = new Bundle();
                Menu data1 = (Menu) data;

                bundle.putString("name",data1.getName());
                navController.navigate(R.id.navigation_searchdetail,bundle);
            }
        });
        List<Menu> o = fromToJson(getLocalJson("menu.json"), new TypeToken<List<Menu>>() {
        }.getType());

                recyclerViewAdapter.setTlist(o);
        binding.recyclerView1.setAdapter(recyclerViewAdapter);
        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        final boolean[] shoucang = {true};
        final boolean[] aixin = {true};
        recyclerViewAdapter2 = new RecyclerViewAdapter<Shipu>(R.layout.home_shipuitem) {
            @Override
            public void convert(MyViewHolder holder, int p) {
                holder.itemView.setTag(tlist.get(p));

                //使用databinding 进行 数据项的装配
                HomeShipuitemBinding homeShipuitemBinding = (HomeShipuitemBinding) holder.getBinding();
//                BitmapFactory.decodeFile("C:\\Users\\elory\\Desktop\\Android\\meishi\\app\\src\\main\\res\\drawable\\ic_hongpei.xml");
//
//                homeViewModel.sid.set();

                homeShipuitemBinding.materialRatingBar.setNumStars(5);
                homeShipuitemBinding.materialRatingBar.setProgress(tlist.get(p).xing*2);
                homeShipuitemBinding.materialRatingBar.setEnabled(false);




                homeShipuitemBinding.imgAixin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (aixin[0]) {

                            homeShipuitemBinding.imgAixin.setImageResource(R.drawable.ic_aixin2);
                        aixin[0] =false;
                            ShouCang shouCang = new ShouCang(tlist.get(p).sid, user);
                            homeViewModel.updateOrInser(shouCang);
                            homeViewModel.updateshoucang(1,tlist.get(p).sid);
                        } else {

                            homeShipuitemBinding.imgAixin.setImageResource(R.drawable.ic_aixin);
                            aixin[0] =true;
                            homeViewModel.updateshoucang(-1,tlist.get(p).sid);
                            ShouCang shouCang = new ShouCang(tlist.get(p).sid, user);
                            homeViewModel.deleteShouCangs(shouCang);
                        }
                        notifyDataSetChanged();
                    }
                });

                homeShipuitemBinding.imgDianzan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.d("shoss",shoucang[0]+"");
                        if (shoucang[0]) {
                            homeShipuitemBinding.imgDianzan.setImageResource(R.drawable.ic_dianzan2);
                            homeViewModel.updatedianzan(1,tlist.get(p).sid);
                            shoucang[0] =false;
                        }else {
                            homeShipuitemBinding.imgDianzan.setImageResource(R.drawable.ic_dianzan);

                            homeViewModel.updatedianzan(-1,tlist.get(p).sid);
                            shoucang[0] =true;
                        }
                        notifyDataSetChanged();

                    }
                });

                homeShipuitemBinding.setShipu(tlist.get(p));

            }

        };
        homeViewModel.getAllShipuLive().observe(getActivity(), new Observer<List<Shipu>>() {
            @Override
            public void onChanged(List<Shipu> shipus) {
                recyclerViewAdapter2.setTlist(shipus);
                recyclerViewAdapter2.notifyDataSetChanged();
            }
        });

        recyclerViewAdapter2.setOnItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view, Object data) {
                NavController navController = Navigation.findNavController(view);
                Bundle bundle = new Bundle();
                Shipu data1 = (Shipu) data;
                bundle.putLong("data",((Shipu) data).sid);
                navController.navigate(R.id.navigation_shipudetail,bundle);
            }
        });
        binding.recyclerView2.setAdapter(recyclerViewAdapter2);
        return root;
    }
    public  <T> T fromToJson(String json,Type listType){
        Gson gson = new Gson();
        T t = null;
        t = gson.fromJson(json,listType);
        return t;
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
    public void onAttach(@NonNull @NotNull Activity activity) {
        super.onAttach(activity);


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}