package com.zero.du.meishi.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.zero.du.meishi.bean.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User... users);
    @Delete
    void deleteUser(User... users);
    @Update
    void updateUser(User... users);
    @Query("DELETE FROM user")
    void deleteAllUsers();

    @Query("SELECT * FROM User WHERE user = :user")
    LiveData<User> getUserLiveById(String user);
    @Query("SELECT * FROM User ")
    LiveData<List<User>> getAllUsersLive();
    @Query("update User set pwd = :s where user =  :id")
    void upatepwd(String s,String id);

}
