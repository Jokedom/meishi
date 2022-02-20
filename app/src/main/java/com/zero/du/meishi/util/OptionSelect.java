package com.zero.du.meishi.util;

import android.graphics.Color;

import androidx.fragment.app.FragmentActivity;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;

public class OptionSelect {

    private OptionsPickerView optionsPickerView;
    private ArrayList<Integer> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();


    public void getInstance(final FragmentActivity context) {

        optionsPickerView = new OptionsPickerBuilder(context, (options1, options2, options3, v) -> {

            listener.onSelect(options1, options2, options3);
            //返回的分别是三个级别的选中位置

        })
                .setCancelText("取消")
                .setSubmitText("确定")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)

                .build();


    }

    public OptionsPickerView getOptionsPickerView() {
        return optionsPickerView;
    }

    public void setOptionsPickerView(OptionsPickerView optionsPickerView) {
        this.optionsPickerView = optionsPickerView;
    }

    public static OnPickerListener listener;

    public void getListener(OnPickerListener listener) {
        this.listener = listener;
    }

    public interface OnPickerListener {

        void onSelect(int position1, int position2, int position3);
    }





}

