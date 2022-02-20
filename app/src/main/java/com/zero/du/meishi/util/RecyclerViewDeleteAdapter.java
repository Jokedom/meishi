package com.zero.du.meishi.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class RecyclerViewDeleteAdapter<T> extends RecyclerView.Adapter<RecyclerViewDeleteAdapter.MyViewHolder>implements View.OnClickListener {




    private boolean isVisible = false;


    private int layoutId;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
//    public List<Password> passwordList =new ArrayList<>();
    private List<Collection> collectionList;


    public List<Boolean> booleanlist=new ArrayList<>();//勾选状态列表
    public List<T> tlist = new ArrayList<>();
    public  interface OnRecyclerViewItemClickListener<T> {
        void onItemClick(View view , T data);
    }
    //删除选中的数据
    public void deletingData() {
        for (int i = 0; i < tlist.size(); i++) {
            //判断是否为勾选项
            if(booleanlist.get(i)!=null && booleanlist.get(i) ) {
                deleteCover(i);

                //移除勾选项
                booleanlist.remove(i);
                notifyItemRemoved(i);
                i--;

            }
        }

    }


    public void cancelAll() {
        for (int i = 0; i < tlist.size(); i++) {
                if(booleanlist.get(i)){
                    booleanlist.set(i,false);
                }else {
                    booleanlist.set(i,true);
                }
        }
        notifyDataSetChanged();
    }

    public void reset() {
        for (int i = 0; i < tlist.size(); i++) {
            booleanlist.set(i,false);
        }

    }

    public void selectAll(){
//        initCheck(true);
        for (int i = 0; i < tlist.size(); i++) {
            booleanlist.set(i,true);
        }
        notifyDataSetChanged();
    }




    public RecyclerViewDeleteAdapter(int layoutid ){
        layoutId=layoutid;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (T) v.getTag());
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),layoutId, parent, false);
        viewDataBinding.getRoot().setOnClickListener(this);
        return new MyViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        convert(holder,position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public abstract void convert(MyViewHolder holder,int p);
    public abstract void deleteCover(int p);

    public void setTlist(List<T> tlist) {
        this.tlist = tlist;
    }

    @Override
    public int getItemCount() {


        return tlist.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private ViewDataBinding binding;

        public ViewDataBinding getBinding() {
            return binding;
        }

        public MyViewHolder(ViewDataBinding itemBinding){
            super(itemBinding.getRoot());
            binding=itemBinding;

        }
    }
}
