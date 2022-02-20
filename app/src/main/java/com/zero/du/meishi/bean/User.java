package com.zero.du.meishi.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "user",indices =@Index(value = {"user"},unique = true))
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="uid",typeAffinity = ColumnInfo.INTEGER)
    public long uid;

    @ColumnInfo(name="user",typeAffinity = ColumnInfo.TEXT)
    public String user;
    @ColumnInfo(name="pwd",typeAffinity = ColumnInfo.TEXT)
    public String pwd;
    @ColumnInfo(name="phone",typeAffinity = ColumnInfo.TEXT)
    public String phone;
    @ColumnInfo(name="staus",typeAffinity = ColumnInfo.INTEGER)
    public int status;

    @Ignore
    public User(long uid, String user, String pwd, String phone, int status) {
        this.uid = uid;
        this.user = user;
        this.pwd = pwd;
        this.phone = phone;
        this.status = status;
    }

    public User(String user, String pwd, String phone, int status) {
        this.user = user;
        this.pwd = pwd;
        this.phone = phone;
        this.status = status;
    }
}
