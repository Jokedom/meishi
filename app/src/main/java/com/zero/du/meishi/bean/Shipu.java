package com.zero.du.meishi.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "shipu")
public class Shipu implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="sid",typeAffinity = ColumnInfo.INTEGER)
    public long sid;
    @ColumnInfo(name="name",typeAffinity = ColumnInfo.TEXT)
    public String name;
    @ColumnInfo(name="leibie",typeAffinity = ColumnInfo.TEXT)
    public String leibie;
    @ColumnInfo(name="xing",typeAffinity = ColumnInfo.INTEGER)
    public int xing;
    @ColumnInfo(name="dianzan",typeAffinity = ColumnInfo.INTEGER)
    public int dianzan;
    @ColumnInfo(name="shoucang",typeAffinity = ColumnInfo.INTEGER)
    public int shoucang;

    @ColumnInfo(name="comment",typeAffinity = ColumnInfo.TEXT)
    public String comment;

    @ColumnInfo(name="cailiao1",typeAffinity = ColumnInfo.TEXT)
    public String cailiao1;
    @ColumnInfo(name="cailiaonum1",typeAffinity = ColumnInfo.TEXT)
    public String cailiaonum1;
    @ColumnInfo(name="cailiao2",typeAffinity = ColumnInfo.TEXT)
    public String cailiao2;
    @ColumnInfo(name="cailiaonum2",typeAffinity = ColumnInfo.TEXT)
    public String cailiaonum2;
    @ColumnInfo(name="cailiao3",typeAffinity = ColumnInfo.TEXT)
    public String cailiao3;
    @ColumnInfo(name="cailiaonum3",typeAffinity = ColumnInfo.TEXT)
    public String cailiaonum3;
    @ColumnInfo(name="cailiao4",typeAffinity = ColumnInfo.TEXT)
    public String cailiao4;
    @ColumnInfo(name="cailiaonum4",typeAffinity = ColumnInfo.TEXT)
    public String cailiaonum4;
    @ColumnInfo(name="cailiao5",typeAffinity = ColumnInfo.TEXT)
    public String cailiao5;
    @ColumnInfo(name="cailiaonum5",typeAffinity = ColumnInfo.TEXT)
    public String cailiaonum5;
    @ColumnInfo(name="cailiao6",typeAffinity = ColumnInfo.TEXT)
    public String cailiao6;
    @ColumnInfo(name="cailiaonum6",typeAffinity = ColumnInfo.TEXT)
    public String cailiaonum6;
    @Ignore
    public Shipu(long sid, String name, String leibie, int xing, int dianzan, int shoucang, String comment, String cailiao1, String cailiaonum1, String cailiao2, String cailiaonum2, String cailiao3, String cailiaonum3, String cailiao4, String cailiaonum4, String cailiao5, String cailiaonum5, String cailiao6, String cailiaonum6) {
        this.sid = sid;
        this.name = name;
        this.leibie = leibie;
        this.xing = xing;
        this.dianzan = dianzan;
        this.shoucang = shoucang;
        this.comment = comment;
        this.cailiao1 = cailiao1;
        this.cailiaonum1 = cailiaonum1;
        this.cailiao2 = cailiao2;
        this.cailiaonum2 = cailiaonum2;
        this.cailiao3 = cailiao3;
        this.cailiaonum3 = cailiaonum3;
        this.cailiao4 = cailiao4;
        this.cailiaonum4 = cailiaonum4;
        this.cailiao5 = cailiao5;
        this.cailiaonum5 = cailiaonum5;
        this.cailiao6 = cailiao6;
        this.cailiaonum6 = cailiaonum6;
    }

    public Shipu(String name, String leibie, int xing, int dianzan, int shoucang, String comment, String cailiao1, String cailiaonum1, String cailiao2, String cailiaonum2, String cailiao3, String cailiaonum3, String cailiao4, String cailiaonum4, String cailiao5, String cailiaonum5, String cailiao6, String cailiaonum6) {
        this.name = name;
        this.leibie = leibie;
        this.xing = xing;
        this.dianzan = dianzan;
        this.shoucang = shoucang;
        this.comment = comment;
        this.cailiao1 = cailiao1;
        this.cailiaonum1 = cailiaonum1;
        this.cailiao2 = cailiao2;
        this.cailiaonum2 = cailiaonum2;
        this.cailiao3 = cailiao3;
        this.cailiaonum3 = cailiaonum3;
        this.cailiao4 = cailiao4;
        this.cailiaonum4 = cailiaonum4;
        this.cailiao5 = cailiao5;
        this.cailiaonum5 = cailiaonum5;
        this.cailiao6 = cailiao6;
        this.cailiaonum6 = cailiaonum6;
    }


    public  String tozan(int dianzan2){
        return "已有"+dianzan2+"人点赞";
    }
    public  String toshou(int shoucang2 ){
        return shoucang2+"人收藏";
    }
}
