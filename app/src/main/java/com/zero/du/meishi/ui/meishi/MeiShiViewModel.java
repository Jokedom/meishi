package com.zero.du.meishi.ui.meishi;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.bigkoo.pickerview.view.OptionsPickerView;
import com.xuexiang.xui.utils.SnackbarUtils;
import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.bean.Shipu;
import com.zero.du.meishi.repository.ShipuRepository;
import com.zero.du.meishi.util.OptionSelect;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MeiShiViewModel extends AndroidViewModel {
    private OptionsPickerView pvOptions;
    private OptionSelect optionSelect;
    private ArrayList<String> leibieitem = new ArrayList<>();
    private ArrayList<String> xingitem = new ArrayList<>();
    private final ShipuRepository repository;

    public final ObservableField<String> uid = new ObservableField<>();
    public final ObservableField<String> xing = new ObservableField<>();
    public final ObservableField<Integer> dian = new ObservableField<>();
    public final ObservableField<Integer> shoucang = new ObservableField<>();
    public final ObservableField<String> shipuname = new ObservableField<>();
    public final ObservableField<String> leibie = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<Long> sid = new ObservableField<>();
    public final ObservableField<String> pwd  = new ObservableField<>();
    public final ObservableField<String> comment = new ObservableField<>();
    public final ObservableField<String> cailiao1 = new ObservableField<>();
    public final ObservableField<String> cailiaonum1 = new ObservableField<>();
    public final ObservableField<String> cailiao2 = new ObservableField<>();
    public final ObservableField<String> cailiaonum2 = new ObservableField<>();
    public final ObservableField<String> cailiao3 = new ObservableField<>();
    public final ObservableField<String> cailiaonum3 = new ObservableField<>();
    public final ObservableField<String> cailiao4 = new ObservableField<>();
    public final ObservableField<String> cailiaonum4 = new ObservableField<>();
    public final ObservableField<String> cailiao5 = new ObservableField<>();
    public final ObservableField<String> cailiaonum5 = new ObservableField<>();
    public final ObservableField<String> cailiao6 = new ObservableField<>();
    public final ObservableField<String> cailiaonum6 = new ObservableField<>();
    private Application application;

    private boolean ISCHANGE=false;
    public MeiShiViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.application =application;

        getOptionData();

        repository = new ShipuRepository(application);
//                                for (int i=0;i<12;i++){
//                    User card = new User(i+"","ddd", "????????????", 1000);
//            repository.insertUser(card);
//        }
    }
    public void initData(){
        xing.set("");
        leibie.set("");
        shipuname.set("");
    }
    public LiveData<List<Shipu>> getAllUserLive(){
        return repository.getAllShipuLive();
    }

    public boolean isISCHANGE() {
        return ISCHANGE;
    }

    public void setISCHANGE(boolean ISCHANGE) {
        this.ISCHANGE = ISCHANGE;
    }

    public void insertshipu(View view) {
        if(ISCHANGE){
            if(!shipuname.get().equals("")&&!leibie.get().equals("")&&!xing.get().equals("")){
                Shipu shipu = new Shipu(sid.get(),shipuname.get(), leibie.get(), Integer.parseInt(xing.get()), dian.get(), shoucang.get(),comment.get(),cailiao1.get(),cailiaonum1.get(),cailiao2.get(),cailiaonum2.get(),cailiao3.get(),cailiaonum3.get(),cailiao4.get(),cailiaonum4.get(),cailiao5.get(),cailiaonum5.get(),cailiao6.get(),cailiaonum6.get());
                repository.updateShipu(shipu);

                SnackbarUtils.Short(view, "????????????!").show();

            }else {
                SnackbarUtils.Short(view, "?????????????????????!").show();
            }
        }else {
            if(!shipuname.get().equals("")&&!leibie.get().equals("")&&!xing.get().equals("")){
                Shipu shipu = new Shipu(shipuname.get(), leibie.get(), Integer.parseInt(xing.get()), 0, 0,"????????????","??????1","??????1","??????2","??????2","??????3","??????3","??????4","??????4","??????5","??????5","??????6","??????6");
                repository.insertShipu(shipu);
                SnackbarUtils.Short(view, "????????????!").show();

            }else {
                SnackbarUtils.Short(view, "?????????????????????!").show();
            }
        }



    }
    private void getOptionData() {
        for (int i = 1; i < 6; i++) {
            xingitem.add(String.valueOf(i));
        }
        leibieitem.add("????????????");
        leibieitem.add("????????????");
        leibieitem.add("????????????");
        leibieitem.add("????????????");
        leibieitem.add("????????????");
        /*--------?????????????????????---------*/
    }

    public void selectOption(FragmentActivity activity) {
        optionSelect = new OptionSelect();
        optionSelect.getInstance(activity);
        pvOptions = optionSelect.getOptionsPickerView();
    }
    public void showleibie(View view) {
        InputMethodManager imm = (InputMethodManager) application.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        pvOptions.setTitleText("????????????");
        pvOptions.setPicker(leibieitem);//???????????????*/
        pvOptions.show();
        optionSelect.getListener(new OptionSelect.OnPickerListener() {
            @Override
            public void onSelect(int position1, int position2, int position3) {
                leibie.set(leibieitem.get(position1));

            }
        });
    }
    public void showxing(View view) {

        InputMethodManager imm = (InputMethodManager) application.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        pvOptions.setTitleText("????????????");
        pvOptions.setPicker(xingitem);//???????????????*/
        pvOptions.show();
        optionSelect.getListener(new OptionSelect.OnPickerListener() {
            @Override
            public void onSelect(int position1, int position2, int position3) {
                xing.set(xingitem.get(position1));

            }
        });

    }
}