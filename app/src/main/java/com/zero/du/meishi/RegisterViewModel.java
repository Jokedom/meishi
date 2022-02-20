package com.zero.du.meishi;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zero.du.meishi.bean.User;
import com.zero.du.meishi.repository.UserRepository;

import org.jetbrains.annotations.NotNull;

public class RegisterViewModel extends AndroidViewModel {

    public final ObservableField<String> username = new ObservableField<>();

    public final ObservableField<String> phone = new ObservableField<>();
    public final ObservableField<String> pwd = new ObservableField<>();
    public final ObservableField<String> pwd2 = new ObservableField<>();
    private final UserRepository repository;
    private Application application;

    public RegisterViewModel(@NonNull @NotNull Application application) {
        super(application);

        this.application = application;
        this.application = application;
        repository = new UserRepository(application);

    }
    public  LiveData<User> query(String q){
       return  repository.queryUser(q);
    }
}