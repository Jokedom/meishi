package com.zero.du.meishi.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.bean.ShipuDetailResult;
import com.zero.du.meishi.dao.ShipuDao;
import com.zero.du.meishi.db.MyDataBase;

import java.util.List;

public class ShipuRepository {
    private ShipuDao shipuDao;

    public ShipuRepository(Context context) {
        MyDataBase instance = MyDataBase.getInstance(context);
        this.shipuDao = instance.getShipuDao();
    }
    public void updateShipu(Shipu... Shipus){
        new UpdateShipuTask(shipuDao).execute(Shipus);
    }
    class UpdateShipuTask extends AsyncTask<Shipu,Void,Void> {

        private ShipuDao shipuDao;
        public UpdateShipuTask(ShipuDao shipuDao){
            this.shipuDao =shipuDao;
        }
        @Override
        protected Void doInBackground(Shipu... Shipus) {
            shipuDao.updateShipu(Shipus);
            return null;
        }
    }
    public LiveData<Shipu> queryShipu(long q){

       return shipuDao.getShipuLiveById(q);

    }
    public LiveData<List<Shipu>> getShipuLiveByleibie(String q){

        return shipuDao.getShipuLiveByleibie(q);

    }
    public LiveData<List<Shipu>> getShipuLiveByname(String q){

        return shipuDao.getShipuLiveByname(q);

    }

    public void updateDianzan(int s,long p){

        new updateDianzanTask(this.shipuDao,s,p).execute("");
    }
    class updateDianzanTask extends AsyncTask<String,Void,Void> {
        int s;
        long p;
        private ShipuDao shipuDao;
        public updateDianzanTask(ShipuDao shipuDao,int u,long p){
            this.shipuDao =shipuDao;
            s=u;
            this.p=p;
        }


        @Override
        protected Void doInBackground(String... strings) {
            shipuDao.updatedianzan(s,p);
            return null;
        }
    }
    public void updateShoucang(int s,long p){

        new updateShoucangTask(this.shipuDao,s,p).execute("");
    }
    class updateShoucangTask extends AsyncTask<String,Void,Void> {
        int s;
        long p;
        private ShipuDao shipuDao;
        public updateShoucangTask(ShipuDao shipuDao,int u,long p){
            this.shipuDao =shipuDao;
            s=u;
            this.p=p;
        }


        @Override
        protected Void doInBackground(String... strings) {
            shipuDao.updateshoucang(s,p);
            return null;
        }
    }
    public void insertShipu(Shipu... Shipus){
        new InsertShipuTask(shipuDao).execute(Shipus);
    }
    public LiveData<List<Shipu>> getAllShipuLive(Shipu... Shipus){
        return shipuDao.getAllShipusLive();
    }
    public void deleteAllShipus(){
        new DeleteAllShipusTask(shipuDao).execute();
    }
    class DeleteAllShipusTask extends AsyncTask<Void,Void,Void>{
        private ShipuDao shipuDao;
        public DeleteAllShipusTask(ShipuDao shipuDao){
            this.shipuDao=shipuDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            shipuDao.deleteAllShipus();
            return null;
        }
    }
    public void deleteShipus(Shipu... Shipus){
        new DeleteShipuTask(shipuDao).execute(Shipus);
    }
    class DeleteShipuTask extends AsyncTask<Shipu,Void,Void >{
        private ShipuDao shipuDao;
        public DeleteShipuTask(ShipuDao shipuDao){
            this.shipuDao=shipuDao;
        }

        @Override
        protected Void doInBackground(Shipu... Shipus) {
            shipuDao.deleteShipu(Shipus);
            return null;
        }
    }
    class InsertShipuTask extends AsyncTask<Shipu,Void,Void> {

        private ShipuDao shipuDao;
        public InsertShipuTask(ShipuDao shipuDao){
            this.shipuDao =shipuDao;
        }
        @Override
        protected Void doInBackground(Shipu... Shipus) {
            shipuDao.insertShipu(Shipus);
            return null;
        }
    }
}
