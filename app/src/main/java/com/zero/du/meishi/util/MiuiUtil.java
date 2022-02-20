package com.zero.du.meishi.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.xuexiang.xui.utils.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public  class MiuiUtil   {
    private static Toast mToast;
    public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
    if (Build.VERSION.SDK_INT >= 21) {
        View decorView = activity.getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);//状态栏颜色设置为透明。
    }
    boolean result = false;
    Window window = activity.getWindow();
    if (window != null) {
        Class clazz = window.getClass();
        try {
            int darkModeFlag = 0;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
            }
            result = true;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
                //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                if (dark) {
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                }
            }
        } catch (Exception e) {

        }
    }
    return result;
}

    public static void toast(Context context,String showmsg){
        if (mToast == null) {
            //将这一句换成下面两句
//            mToast = Toast.makeText(context, showmsg, duration);
            mToast=Toast.makeText(context,null,Toast.LENGTH_SHORT);
            mToast.setText(showmsg);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(showmsg);
        }
        mToast.show();
    }
    public static String toHide(String s) {
        String str = s.substring(0, 1);
        String str2 = s.substring(s.length() - 1);
        if (s.length() >= 7) {
            str = s.substring(0, 2);
            str += "***";
            str += s.substring(s.length() - 2);
            ;

        } else if (s.length() >= 5) {
            str += "***";
            str += str2;

        } else if (s.length() > 1) {
            for (int i = 0; i < s.length() - 1; i++) {
                str += "*";
            }

        } else {
            str = "*";

        }
        return str;
    }

}
