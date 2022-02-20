package com.zero.du.meishi.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.bean.ShipuDetailResult;

import java.util.List;

@Dao
public interface ShipuDao {
    @Insert
    void insertShipu(Shipu... shipus);
    @Delete
    void deleteShipu(Shipu... shipus);
    @Update
    void updateShipu(Shipu... shipus);
    @Query("DELETE FROM shipu")
    void deleteAllShipus();

    @Query("SELECT * FROM Shipu WHERE sid = :user")
    LiveData<Shipu> getShipuLiveById(long user);
    @Query("SELECT * FROM Shipu WHERE leibie = :user")
    LiveData<List<Shipu>> getShipuLiveByleibie(String user);
    @Query("SELECT * FROM Shipu WHERE name like :user")
    LiveData<List<Shipu>> getShipuLiveByname(String user);


    @Query("SELECT * FROM Shipu ")
    LiveData<List<Shipu>> getAllShipusLive();

    @Query("update shipu set dianzan = dianzan+:ss where sid =:dd")
    void updatedianzan(int ss,long dd);
    @Query("update shipu set shoucang = shoucang+:ss where sid =:dd")
    void updateshoucang(int ss,long dd);

}
