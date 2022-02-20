package com.zero.du.meishi;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.bean.User;
import com.zero.du.meishi.repository.ShipuRepository;
import com.zero.du.meishi.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;


//初始化整个app
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }
    private void init() {

        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE); //私有数据
        boolean init = sharedPreferences.getBoolean("init", false);
        if(!init){
            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
            editor.putBoolean("init",true );
            editor.commit();//提交修改

            //初始化创建超管用户
            UserRepository userRepository = new UserRepository(this);
            User user = new User("admin","admin","17369678888",1);//状态1表示初始化
            userRepository.insertUser(user);
            ShipuRepository shipuRepository = new ShipuRepository(this);
            List<Shipu> shipus = new ArrayList<>();
            shipus.add(new Shipu("鱼香肉丝", "家常小炒", 4, 110, 120,"美食大全","材料1","数量1","材料2","数量2","材料3","数量3","材料4","数量4","材料5","数量5","材料6","数量6"));
            shipus.add(new Shipu("芒果班戟", "烘焙入门", 5, 1210, 3210,"美食大全","材料1","数量1","材料2","数量2","材料3","数量3","材料4","数量4","材料5","数量5","材料6","数量6"));
            shipus.add(new Shipu("兰州拉面", "面食点心", 3, 1010, 2210,"美食大全","材料1","数量1","材料2","数量2","材料3","数量3","材料4","数量4","材料5","数量5","材料6","数量6"));
            shipus.add(new Shipu("水果沙拉", "健康饮食", 4, 1510, 5210,"美食大全","材料1","数量1","材料2","数量2","材料3","数量3","材料4","数量4","材料5","数量5","材料6","数量6"));
            shipus.add(new Shipu("脆皮高粱卷", "甜点小吃", 5, 2210, 410,"美食大全","材料1","数量1","材料2","数量2","材料3","数量3","材料4","数量4","材料5","数量5","材料6","数量6"));
            for(Shipu shipu :shipus){
                shipuRepository.insertShipu(shipu);
            }

        }

    }
}
