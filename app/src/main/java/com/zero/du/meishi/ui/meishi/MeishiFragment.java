package com.zero.du.meishi.ui.meishi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.zero.du.meishi.R;
import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.bean.ShipuDetailResult;
import com.zero.du.meishi.databinding.DeletePopupwindowBinding;
import com.zero.du.meishi.databinding.FragmentMeishiadminBinding;
import com.zero.du.meishi.databinding.QuerymsItemBinding;
import com.zero.du.meishi.repository.ShipuRepository;
import com.zero.du.meishi.util.RecyclerViewAdapter;
import com.zero.du.meishi.util.RecyclerViewDeleteAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MeishiFragment extends Fragment {

    private MeiShiViewModel homeViewModel;
    private FragmentMeishiadminBinding binding;
    private RecyclerViewDeleteAdapter<Shipu> recyclerViewAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(MeiShiViewModel.class);

        binding = FragmentMeishiadminBinding.inflate(inflater, container, false);
        binding.recyQuery.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapter = new RecyclerViewDeleteAdapter<Shipu>(R.layout.queryms_item) {
            @Override
            public void convert(MyViewHolder holder, int p) {
                holder.itemView.setTag(tlist.get(p));

                //??????databinding ?????? ??????????????????
                QuerymsItemBinding queryuserItemBinding = (QuerymsItemBinding) holder.getBinding();

                if(isVisible()){
                    queryuserItemBinding.checkBox.setVisibility(View.VISIBLE);

                }else {
                    queryuserItemBinding.checkBox.setVisibility(View.GONE);

                }

                queryuserItemBinding.checkBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
                    //?????????????????????

                    booleanlist.set(p, isChecked);

                }));



                queryuserItemBinding.setShipu(tlist.get(p));
                if(booleanlist!=null){
                    queryuserItemBinding.checkBox.setChecked(booleanlist.get(p));
                }
            }

            @Override
            public void deleteCover(int p) {
                ShipuRepository userRepository = new ShipuRepository(getContext());
                userRepository.deleteShipus(tlist.get(p));
                tlist.remove(p);
            }
        };

        binding.recyQuery.setAdapter(recyclerViewAdapter);
        homeViewModel.getAllUserLive().observe(getActivity(), new Observer<List<Shipu>>() {
            @Override
            public void onChanged(List<Shipu> users) {
                for (int i = 0; i < users.size(); i++) {
                    recyclerViewAdapter.booleanlist.add(false);
                }
                recyclerViewAdapter.setTlist(users);
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewDeleteAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                NavController navController = Navigation.findNavController(view);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data",(Shipu)data);
                navController.navigate(R.id.navigation_xiugaishipu,bundle);
            }
        });
        View root = binding.getRoot();
        setHasOptionsMenu(true);

        return root;
    }

    private PopupWindow pw_delete;
    private void showDeletePopupWindow(){
        //??????PopupWindow???????????????
        Log.d("ssss","??????vs");
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.delete_popupwindow,null);
        DeletePopupwindowBinding deletePopupwindowBinding = DeletePopupwindowBinding.inflate(getLayoutInflater());
        pw_delete = new PopupWindow(deletePopupwindowBinding.getRoot() , ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

//        pw_delete.setAnimationStyle(R.style.popupAnimation);
        pw_delete.setOutsideTouchable(false);
        pw_delete.setFocusable(false);

        pw_delete.showAtLocation(binding.recyQuery, Gravity.BOTTOM,0,0);

        //??????????????????????????????
        deletePopupwindowBinding.deletebutton.setOnClickListener((v)->{
            new AlertDialog.Builder(getActivity())
                    .setTitle("?????????????????????")
                    .setMessage("?????????")
                    .setPositiveButton("???", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            recyclerViewAdapter.deletingData();
                        }
                    })
                    .setNegativeButton("???", null)
                    .show();
//            new MaterialDialog.Builder(getContext()).content(R.string.delete).positiveText(R.string.yes)
//                    .negativeText(R.string.no)
//                    .show();
        });
        TextView allcheckbutton = deletePopupwindowBinding.allcheckbutton;
        deletePopupwindowBinding.allcheckbutton.setOnClickListener((v)->{
            if("??????".equals(allcheckbutton.getText().toString().trim())){
                allcheckbutton.setText("??????");
                recyclerViewAdapter.selectAll();

            }else {
                allcheckbutton.setText("??????");
                recyclerViewAdapter.cancelAll();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.meishiadmin, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        NavController navController = Navigation.findNavController(getView());
        switch (item.getItemId()){
            case R.id.action_tmjia:
                navController.navigate(R.id.navigation_addshipu);
                break;

            case R.id.action_meishiweihu:

                if(recyclerViewAdapter.isVisible()){

                    recyclerViewAdapter.setVisible(false);
                    dismissDeletePopupWindow();

                    binding.constraintLayout4.setVisibility(View.GONE);
                    recyclerViewAdapter.notifyDataSetChanged();
                    recyclerViewAdapter.reset();
                }else {

                    recyclerViewAdapter.setVisible(true);
                    recyclerViewAdapter.notifyDataSetChanged();
                    showDeletePopupWindow();
                    binding.constraintLayout4.setVisibility(View.VISIBLE);
                }

                break;

        }
        return false;
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
        dismissDeletePopupWindow();
        binding = null;
    }
}