package com.zero.du.meishi.ui.mine;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.zero.du.meishi.bean.SCResult;
import com.zero.du.meishi.bean.User;
import com.zero.du.meishi.repository.ShoucangRepository;
import com.zero.du.meishi.repository.UserRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MineViewModel extends AndroidViewModel {


    public final ObservableField<String> oldpwd = new ObservableField<>();
    public final ObservableField<String> newpwd = new ObservableField<>();
    public final ObservableField<String> newpwd2 = new ObservableField<>();


    public final ObservableField<String> username = new ObservableField<>();
    public final ObservableField<String> phone = new ObservableField<>();
    private final ShoucangRepository shoucangRepository;
    private final UserRepository userRepository;

    private Application application;

    public MineViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.application = application;
        shoucangRepository = new ShoucangRepository(application);
        userRepository = new UserRepository(application);

    }
    public void loginuser(String q,String s){
        userRepository.updatepwd(q, s);
    }
    public LiveData<User> queryUser(String q){
        return userRepository.queryUser(q);
    }
    public LiveData<List<SCResult>> getSpDetailByname(String s){
        return shoucangRepository.queryShouCang(s);
    }


}