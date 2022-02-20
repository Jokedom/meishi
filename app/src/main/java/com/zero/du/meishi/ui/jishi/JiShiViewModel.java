package com.zero.du.meishi.ui.jishi;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.bean.ShipuDetailResult;
import com.zero.du.meishi.repository.ShipuRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JiShiViewModel extends AndroidViewModel {


    private final Application application;
    private final ShipuRepository shipuRepository;
    public final ObservableField<Long> sid = new ObservableField<>();


    public JiShiViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.application =application;
        shipuRepository = new ShipuRepository(application);

    }
    public LiveData<List<Shipu>> getShipuLiveByleibie(String q){
        return shipuRepository.getShipuLiveByleibie(q);
    }
    public LiveData<List<Shipu>> getAllShipuLive(){
        return shipuRepository.getAllShipuLive();
    }
}