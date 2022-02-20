package com.zero.du.meishi.bean;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "shoucang")
public class ShouCang implements Serializable {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name="sid",typeAffinity = ColumnInfo.INTEGER)
    public long sid;
    @ColumnInfo(name="user",typeAffinity = ColumnInfo.TEXT)
    public String user;

    public ShouCang(long sid, String user) {
        this.sid = sid;
        this.user = user;
    }

}
