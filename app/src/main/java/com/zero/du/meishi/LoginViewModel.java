package com.zero.du.meishi;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.zero.du.meishi.bean.User;
import com.zero.du.meishi.repository.UserRepository;


public class LoginViewModel extends AndroidViewModel {
    private final UserRepository repository;
    private Application application;

    public final ObservableField<String> user = new ObservableField<>();
    public final ObservableField<String> pwd = new ObservableField<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = new UserRepository(application);
        user.set("");
        pwd.set("");
    }
    //获取用户信息
    public LiveData<User> getUserbean(String q) {
        return repository.queryUser(q);
    }
}
