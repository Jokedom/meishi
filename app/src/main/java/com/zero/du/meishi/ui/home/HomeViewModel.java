package com.zero.du.meishi.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.bean.ShipuDetailResult;
import com.zero.du.meishi.bean.ShouCang;
import com.zero.du.meishi.repository.ShipuRepository;
import com.zero.du.meishi.repository.ShoucangRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {


    private final Application application;
    private final ShipuRepository shipuRepository;
    public final ObservableField<Long> sid = new ObservableField<>();

    private final ShoucangRepository shoucangRepository;

    public HomeViewModel(@NonNull @NotNull Application application) {
        super(application);

        this.application =application;
        shipuRepository = new ShipuRepository(application);

        shoucangRepository = new ShoucangRepository(application);
//        for (int i = 1; i < 10; i++) {
//            Shipu shipu = new Shipu("千库餐厅", "甜点小吃", (int) i/2, 26*i, 222*i);
//            shipuRepository.insertShipu(shipu);
//        }
//        for (int i = 1; i < 10; i++) {
//            SPdetail shipu = new SPdetail(i,"sdsds","材料"+i,"材料数量"+i,"2材料"+i,"2材料数量"+i,"3材料"+i,"3材料数量"+i,
//                    "4材料"+i,"4材料数量"+i,"5材料"+i,"5材料数量"+i,"6材料"+i,"6材料数量"+i);
//            sPdetailRepository.insertSPdetail(shipu);
//        }
    }
    public void updatedianzan(int s,long d){
        shipuRepository.updateDianzan(s,d);

    }
    public void updateshoucang(int s,long d){
        shipuRepository.updateShoucang(s,d);
    }

    public LiveData<List<Shipu>> getAllShipuLive(){
        return shipuRepository.getAllShipuLive();
    }

    public void updateOrInser(ShouCang shouCang){

         shoucangRepository.updateOrInser(shouCang);
    }
    public void deleteShouCangs(ShouCang shouCang){

        shoucangRepository.deleteShouCangs(shouCang);
    }
    public  LiveData<ShouCang> find(long user){

        return shoucangRepository.find(user);
    }
    public  LiveData<ShouCang> find2(String user){

        return shoucangRepository.find2(user);
    }
    public LiveData<Shipu> queryShipu(){
        return shipuRepository.queryShipu(sid.get());
    }
    public LiveData<List<Shipu>> getSpDetailByname(String s){
        return shipuRepository.getShipuLiveByname("%"+s+"%");
    }
    public LiveData<List<Shipu>> getSpDetailByleibie(String s){
        return shipuRepository.getShipuLiveByleibie(s);
    }
}