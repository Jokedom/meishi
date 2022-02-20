package com.zero.du.meishi.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.zero.du.meishi.bean.SCResult;
import com.zero.du.meishi.bean.ShouCang;
import com.zero.du.meishi.dao.ShoucangDao;
import com.zero.du.meishi.dao.UserDao;
import com.zero.du.meishi.db.MyDataBase;

import java.util.List;

public class ShoucangRepository {
    private ShoucangDao shoucangDao;

    public ShoucangRepository(Context context) {
        MyDataBase instance = MyDataBase.getInstance(context);
        this.shoucangDao = instance.getShoucangDao();
    }
    public void updateShouCang(ShouCang... ShouCangs){
        new UpdateShouCangTask(shoucangDao).execute(ShouCangs);
    }
    class UpdateShouCangTask extends AsyncTask<ShouCang,Void,Void> {

        private ShoucangDao shoucangDao;
        public UpdateShouCangTask(ShoucangDao shoucangDao){
            this.shoucangDao =shoucangDao;
        }
        @Override
        protected Void doInBackground(ShouCang... ShouCangs) {
            shoucangDao.updateShouCang(ShouCangs);
            return null;
        }
    }
    public LiveData<List<SCResult>> queryShouCang(String q){
       return shoucangDao.getShouCangLiveById(q);
    }

    public  LiveData<ShouCang> find(long user){
        return shoucangDao.find(user);
    }
    public  LiveData<ShouCang> find2(String user){
        return shoucangDao.find2(user);
    }
    public void insertShouCang(ShouCang... ShouCangs){
        new InsertShouCangTask(shoucangDao).execute(ShouCangs);
    }
    public LiveData<List<SCResult>> getAllShouCangLive(ShouCang... ShouCangs){
        return shoucangDao.getAllShouCangsLive();
    }
    public void deleteAllShouCangs(){
        new DeleteAllShouCangsTask(shoucangDao).execute();
    }
    class DeleteAllShouCangsTask extends AsyncTask<Void,Void,Void>{
        private ShoucangDao shoucangDao;
        public DeleteAllShouCangsTask(ShoucangDao shoucangDao){
            this.shoucangDao=shoucangDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            shoucangDao.deleteAllShouCangs();
            return null;
        }
    }
    public void deleteShouCangs(ShouCang... ShouCangs){
        new DeleteShouCangTask(shoucangDao).execute(ShouCangs);
    }
    public void delete(long s){

        new DeleteTask(this.shoucangDao,s).execute("");
    }
    class DeleteTask extends AsyncTask<String,Void,Void> {
        long s,p;
        private ShoucangDao shoucangDao;
        public DeleteTask(ShoucangDao shoucangDao,long u){
            this.shoucangDao =shoucangDao;
            s=u;

        }


        @Override
        protected Void doInBackground(String... strings) {
            shoucangDao.delete(s);
            return null;
        }
    }
    class DeleteShouCangTask extends AsyncTask<ShouCang,Void,Void >{
        private ShoucangDao shoucangDao;
        public DeleteShouCangTask(ShoucangDao shoucangDao){
            this.shoucangDao=shoucangDao;
        }

        @Override
        protected Void doInBackground(ShouCang... ShouCangs) {
            shoucangDao.deleteShouCang(ShouCangs);
            return null;
        }
    }
    public void updateOrInser(ShouCang shouCang){
        new UpdateOrInsertTask(shoucangDao).execute(shouCang);
    }

    class UpdateOrInsertTask extends AsyncTask<ShouCang,Void,Void >{
        private ShoucangDao shoucangDao;
        public UpdateOrInsertTask(ShoucangDao shoucangDao){
            this.shoucangDao=shoucangDao;
        }

        @Override
        protected Void doInBackground(ShouCang... shouCangs) {
            shoucangDao.insertOrUpdate(shouCangs[0]);
            return null;
        }
    }
    class InsertShouCangTask extends AsyncTask<ShouCang,Void,Void> {

        private ShoucangDao shoucangDao;
        public InsertShouCangTask(ShoucangDao shoucangDao){
            this.shoucangDao =shoucangDao;
        }
        @Override
        protected Void doInBackground(ShouCang... ShouCangs) {
            shoucangDao.insertShouCang(ShouCangs);
            return null;
        }
    }
}
