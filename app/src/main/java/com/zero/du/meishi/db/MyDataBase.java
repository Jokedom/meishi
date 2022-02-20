package com.zero.du.meishi.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.bean.ShouCang;
import com.zero.du.meishi.bean.User;
import com.zero.du.meishi.dao.ShipuDao;
import com.zero.du.meishi.dao.ShoucangDao;
import com.zero.du.meishi.dao.UserDao;


@Database(entities = {Shipu.class, User.class,ShouCang.class},version = 1,exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    private static MyDataBase mInstance;
    private static final String DATABASE = "meishi.db";
    //单例模式创建
    public static synchronized  MyDataBase getInstance(Context context){
        if(mInstance==null){
            mInstance= Room.databaseBuilder(context.getApplicationContext(),
                    MyDataBase.class,DATABASE)
                    .build();
        }
        return mInstance;
    }
    public abstract ShipuDao getShipuDao();
    public abstract UserDao getUserDao();
    public abstract ShoucangDao getShoucangDao();

}
