package com.zero.du.meishi.ui.jishi;

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


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kunminx.linkage.bean.DefaultGroupedItem;
import com.xuexiang.xui.utils.SnackbarUtils;
import com.zero.du.meishi.R;
import com.zero.du.meishi.bean.Menu;
import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.databinding.FragmentJishiBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JiShiFragment extends Fragment {

    private JiShiViewModel jiShiViewModel;
    private FragmentJishiBinding binding;
    private List<DefaultGroupedItem> items;
    private List<Shipu> shipus1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        jiShiViewModel =
                new ViewModelProvider(this).get(JiShiViewModel.class);

        binding = FragmentJishiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getGroupItems();

//        binding.linkage.setScrollSmoothly(true);

//        binding.linkage.setGridMode(!binding.linkage.isGridMode());
        return root;
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            int intmsg = msg.what;
            switch(intmsg){
                case 0:

                    break;
                case 1:
                    final List<Menu>[] o = new List[]{new ArrayList<>()};
                    if(isAdded()) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                o[0] = fromToJson(getLocalJson("menu.json"), new TypeToken<List<Menu>>() {
                                }.getType());
                                items = new ArrayList<>();
                                for (int i = 0; i < 5; i++) {


                                    items.add(new com.kunminx.linkage.bean.DefaultGroupedItem(true, o[0].get(i).getName()));
                                    for(Shipu shipu:shipus1){
                                        if(shipu.leibie.equals(o[0].get(i).getName())){
                                            items.add(new com.kunminx.linkage.bean.DefaultGroupedItem(new com.kunminx.linkage.bean.DefaultGroupedItem.ItemInfo(
                                                    shipu.name, shipu.leibie, shipu.sid+""))
                                            );
                                        }
                                    }

                                }
                                binding.linkage.init(items);

                                binding.linkage.setScrollSmoothly(true);
                                binding.linkage.setGridMode(!binding.linkage.isGridMode());
                                binding. linkage.setDefaultOnItemBindListener(
                                        (primaryHolder, primaryClickView, title) -> {
                                            //一级列表点击
//                                SnackbarUtils.Short(primaryClickView, title).show();
                                        },
                                        (primaryHolder, title) -> {
                                            //一级列表样式设置
                                            //TODO

                                        },
                                        (secondaryHolder, item) -> {
                                            //二级列表点击
                                            secondaryHolder.getView(R.id.level_2_item).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    NavController navController = Navigation.findNavController(view);
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("data",item.info.getTitle());
                                                    navController.navigate(R.id.navigation_searchdetail,bundle);
                                                }
                                            });

                                        },
                                        (headerHolder, item) -> {
                                            //TODO
                                        },
                                        (footerHolder, item) -> {
                                            //TODO
                                        }
                                );
                            }

                        });
                    }


                    break;
            }
        }
    };
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void getGroupItems() {



            jiShiViewModel.getAllShipuLive().observe(getActivity(), new Observer<List<Shipu>>() {
                @Override
                public void onChanged(List<Shipu> shipus) {
                    shipus1 = shipus;
                    handler.sendEmptyMessage(1);
                }
            });




    }
    public  <T> T fromToJson(String json, Type listType){
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
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}