package com.zero.du.meishi.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.zero.du.meishi.bean.SCResult;
import com.zero.du.meishi.bean.ShouCang;
import com.zero.du.meishi.bean.ShipuDetailResult;

import java.util.List;

@Dao
public interface ShoucangDao {
    @Insert
    void insertShouCang(ShouCang... shipus);
    @Delete
    void deleteShouCang(ShouCang... shipus);
    @Update
    void updateShouCang(ShouCang... shipus);
    @Query("DELETE FROM shipu")
    void deleteAllShouCangs();
    @Query("SELECT * FROM shoucang  WHERE sid = :user")
    LiveData<ShouCang> find(long user);
    @Query("SELECT * FROM shoucang  WHERE user = :user")
    LiveData<ShouCang> find2(String user);
    @Query("SELECT * FROM shoucang  WHERE sid = :user")
    ShouCang find3(long user);
    @Query("SELECT shipu.sid ,name,leibie,dianzan,shoucang,uid FROM shoucang JOIN shipu on shoucang.sid=shipu.sid join user on user.user = shoucang.user WHERE user.user = :user")
    LiveData<List<SCResult>> getShouCangLiveById(String user);
    @Query("SELECT shipu.sid ,name,leibie,dianzan,shoucang,uid FROM shoucang JOIN shipu on shoucang.sid=shipu.sid join user on user.user = shoucang.user")
    LiveData<List<SCResult>> getAllShouCangsLive();

    @Query("delete from shoucang where sid =:d")
    void delete(long d);
    default void insertOrUpdate(ShouCang items) {
        ShouCang scResultLiveData = find3(items.sid);
        if (scResultLiveData == null){
            insertShouCang(items);
        }
        else{
            updateShouCang(items);
        }

    }

}
