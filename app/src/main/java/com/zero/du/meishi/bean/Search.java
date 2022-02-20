package com.zero.du.meishi.bean;

public class Search {


    /**
     * name : 烘焙入门
     * icon : @drawable/ic_hongpei
     */

    public String lishi;
    public String remen;


    public Search(String lishi, String remen) {
        this.lishi = lishi;
        this.remen = remen;
    }
    public Search( String remen) {

        this.remen = remen;
    }
}
