package com.zero.du.meishi.ui.mine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zero.du.meishi.R;
import com.zero.du.meishi.bean.Menu;
import com.zero.du.meishi.bean.SCResult;
import com.zero.du.meishi.databinding.DeletePopupwindowBinding;
import com.zero.du.meishi.databinding.FragmentSearchdetailBinding;
import com.zero.du.meishi.databinding.ShoucangdetailitemBinding;
import com.zero.du.meishi.repository.ShoucangRepository;
import com.zero.du.meishi.util.RecyclerViewDeleteAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ShoucangFragment extends Fragment {

    private MineViewModel homeViewModel;
    private FragmentSearchdetailBinding binding;
    private   Menu menu;
    private RecyclerViewDeleteAdapter<SCResult> recyclerViewAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(MineViewModel.class);

        binding = FragmentSearchdetailBinding.inflate(inflater, container, false);
        BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
        viewById.setVisibility(View.GONE);
//        activity.getSupportActionBar().hide();
//        binding.recyclerView2.setLayoutManager(getFlexboxLayoutManager(getContext()));
        binding.recy.setLayoutManager(new LinearLayoutManager(getActivity()));
        SharedPreferences share=getActivity().getSharedPreferences("user", MODE_PRIVATE);
        String user = share.getString("id", "");
        recyclerViewAdapter = new RecyclerViewDeleteAdapter<SCResult>(R.layout.shoucangdetailitem) {
            @Override
            public void convert(MyViewHolder holder, int p) {
                holder.itemView.setTag(tlist.get(p));

                //使用databinding 进行 数据项的装配
                 ShoucangdetailitemBinding searchdetailitemBinding= (ShoucangdetailitemBinding) holder.getBinding();
                 searchdetailitemBinding.setShipu(tlist.get(p));
                if(isVisible()){
                    searchdetailitemBinding.checkBox.setVisibility(View.VISIBLE);

                }else {
                    searchdetailitemBinding.checkBox.setVisibility(View.GONE);

                }

                searchdetailitemBinding.checkBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
                    //判断是否要全选

                    booleanlist.set(p, isChecked);

                }));
                if(booleanlist!=null){
                    searchdetailitemBinding.checkBox.setChecked(booleanlist.get(p));
                }
            }

            @Override
            public void deleteCover(int p) {
                ShoucangRepository userRepository = new ShoucangRepository(getContext());

                userRepository.delete(tlist.get(p).sid);
                tlist.remove(p);
            }

        };
//        List<Search> searches= new ArrayList<>();


//        Log.d("sssssss",searches.get(0).remen);


//        recyclerViewAdapter.notifyDataSetChanged();
//        List<Menu> o = fromToJson(getLocalJson("menu.json"), new TypeToken<List<Menu>>() {
//        }.getType())


        binding.recy.setAdapter(recyclerViewAdapter);
        homeViewModel.getSpDetailByname(user).observe(getActivity(), new Observer<List<SCResult>>() {
            @Override
            public void onChanged(List<SCResult> scResults) {
//                long sid = scResults.get(0).sid;
//                Log.d("ttttttt",sid+"");
                for (int i = 0; i < scResults.size(); i++) {
                    recyclerViewAdapter.booleanlist.add(false);
                }
                recyclerViewAdapter.setTlist(scResults);
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewDeleteAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                NavController navController = Navigation.findNavController(view);
                Bundle bundle = new Bundle();
                SCResult data1 = (SCResult) data;

                bundle.putLong("data", data1.sid);
                bundle.putInt("shi",1);
                navController.navigate(R.id.navigation_shipudetail,bundle);
            }
        });
//        new Qidong(recyclerViewAdapter,searches).execute("");
        View root = binding.getRoot();
        setHasOptionsMenu(true);
        return root;
    }
    private PopupWindow pw_delete;
    private void showDeletePopupWindow(){
        //加载PopupWindow的布局文件
        Log.d("ssss","加载vs");
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.delete_popupwindow,null);
        DeletePopupwindowBinding deletePopupwindowBinding = DeletePopupwindowBinding.inflate(getLayoutInflater());
        pw_delete = new PopupWindow(deletePopupwindowBinding.getRoot() , ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

//        pw_delete.setAnimationStyle(R.style.popupAnimation);
        pw_delete.setOutsideTouchable(false);
        pw_delete.setFocusable(false);

        pw_delete.showAtLocation(binding.recy, Gravity.BOTTOM,0,0);

        //为删除和全选绑定事件
        deletePopupwindowBinding.deletebutton.setOnClickListener((v)->{
            new AlertDialog.Builder(getActivity())
                    .setTitle("你确定要删除吗")
                    .setMessage("确定吗")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            recyclerViewAdapter.deletingData();
                        }
                    })
                    .setNegativeButton("否", null)
                    .show();

        });
        TextView allcheckbutton = deletePopupwindowBinding.allcheckbutton;
        deletePopupwindowBinding.allcheckbutton.setOnClickListener((v)->{
            if("全选".equals(allcheckbutton.getText().toString().trim())){
                allcheckbutton.setText("反选");
                recyclerViewAdapter.selectAll();

            }else {
                allcheckbutton.setText("全选");
                recyclerViewAdapter.cancelAll();
            }
        });

    }
    @Override
    public void onAttach(@NonNull @NotNull Activity activity) {
        super.onAttach(activity);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        NavController navController = Navigation.findNavController(getView());
        switch (item.getItemId()){

            case R.id.action_delete:

                if(recyclerViewAdapter.isVisible()){

                    recyclerViewAdapter.setVisible(false);
                    dismissDeletePopupWindow();
                    BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
                    viewById.setVisibility(View.VISIBLE);
                    binding.constraintLayout4.setVisibility(View.GONE);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerViewAdapter.reset();
                }else {
                    BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
                    viewById.setVisibility(View.GONE);
                    recyclerViewAdapter.setVisible(true);
                    recyclerViewAdapter.notifyDataSetChanged();
                    showDeletePopupWindow();
                    binding.constraintLayout4.setVisibility(View.VISIBLE);
                }

                break;

        }
        return false;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull android.view.Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.shoucangadmin, menu);
    }
    private void dismissDeletePopupWindow(){
        if(pw_delete!=null&&pw_delete.isShowing()){
            pw_delete.dismiss();
            pw_delete = null;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        dismissDeletePopupWindow();
        BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
        viewById.setVisibility(View.VISIBLE);
    }

}