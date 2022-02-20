package com.zero.du.meishi.bean;

public class SCResult {

    public long sid;


    public   String name;
    public   String leibie;

    public int dianzan;
    public int shoucang;
    public long uid;

    public  String tozan(int dianzan2){
        return "已有"+dianzan2+"人点赞";
    }
    public  String toshou(int shoucang2 ){
        return shoucang2+"人收藏";
    }
}
