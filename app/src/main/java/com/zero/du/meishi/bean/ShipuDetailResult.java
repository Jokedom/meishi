package com.zero.du.meishi.bean;

import java.io.Serializable;

public class ShipuDetailResult implements Serializable {

    public    long sid;
    public  String comment;
    public   String cailiao1;
    public    String cailiaonum1;
    public   String cailiao2;
    public  String cailiaonum2;
    public    String cailiao3;
    public   String cailiaonum3;
    public   String cailiao4;
    public   String cailiaonum4;
    public   String cailiao5;
    public   String cailiaonum5;
    public   String cailiao6;
    public   String cailiaonum6;
    public   String name;
    public   String leibie;
    public int xing;
    public int dianzan;
    public int shoucang;

    public  String tozan(int dianzan2){
        return "已有"+dianzan2+"人点赞";
    }
    public  String toshou(int shoucang2 ){
        return shoucang2+"人收藏";
    }
}
