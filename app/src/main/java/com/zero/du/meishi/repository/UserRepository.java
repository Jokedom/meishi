package com.zero.du.meishi.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.zero.du.meishi.bean.User;
import com.zero.du.meishi.dao.UserDao;
import com.zero.du.meishi.db.MyDataBase;

import java.util.List;

public class UserRepository {
    private UserDao userDao;

    public UserRepository(Context context) {
        MyDataBase instance = MyDataBase.getInstance(context);
        this.userDao = instance.getUserDao();
    }
    public void updateUser(User... Users){
        new UpdateUserTask(userDao).execute(Users);
    }
    class UpdateUserTask extends AsyncTask<User,Void,Void> {

        private UserDao userDao;
        public UpdateUserTask(UserDao userDao){
            this.userDao =userDao;
        }
        @Override
        protected Void doInBackground(User... Users) {
            userDao.updateUser(Users);
            return null;
        }
    }
    public LiveData<User> queryUser(String q){

       return userDao.getUserLiveById(q);

    }


    public void insertUser(User... Users){
        new InsertUserTask(userDao).execute(Users);
    }
    public LiveData<List<User>> getAllUserLive(User... Users){
        return userDao.getAllUsersLive();
    }
    public void deleteAllUsers(){
        new DeleteAllUsersTask(userDao).execute();
    }
    class DeleteAllUsersTask extends AsyncTask<Void,Void,Void>{
        private UserDao userDao;
        public DeleteAllUsersTask(UserDao userDao){
            this.userDao=userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAllUsers();
            return null;
        }
    }

    public void deleteUsers(User... Users){
        new DeleteUserTask(userDao).execute(Users);
    }
    class DeleteUserTask extends AsyncTask<User,Void,Void >{
        private UserDao userDao;
        public DeleteUserTask(UserDao userDao){
            this.userDao=userDao;
        }

        @Override
        protected Void doInBackground(User... Users) {
            userDao.deleteUser(Users);
            return null;
        }
    }
    public void updatepwd(String s,String p){

        new UpdatePwdTask(this.userDao,s,p).execute(s);
    }
    class UpdatePwdTask extends AsyncTask<String,Void,Void> {
        String s,p;
        private UserDao userDao;
        public UpdatePwdTask(UserDao userDao,String u,String p){
            this.userDao =userDao;
            s=u;
            this.p=p;
        }


        @Override
        protected Void doInBackground(String... strings) {
            userDao.upatepwd(s,p);
            return null;
        }
    }
    class InsertUserTask extends AsyncTask<User,Void,Void> {

        private UserDao userDao;
        public InsertUserTask(UserDao userDao){
            this.userDao =userDao;
        }
        @Override
        protected Void doInBackground(User... Users) {
            userDao.insertUser(Users);
            return null;
        }
    }
}
