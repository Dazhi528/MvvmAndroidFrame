package com.dazhi.sample;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;
import com.dazhi.libroot.root.RootVmActivity;
import com.dazhi.sample.db.DbPerson;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.functions.Consumer;

public class SampleActivity extends RootVmActivity<VmMain> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initConfig(TextView tvToolTitle) {
        // 初始化ViewModel
        vm=ViewModelProviders.of(this).get(VmMain.class);
        // 设置标题
        if(tvToolTitle!=null){
            tvToolTitle.setText("我的第一个MVVM架构");
        }
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initViewAndDataAndEvent() {
        super.initViewAndDataAndEvent();
        // UI
        final TextView tvMainShowDbData=findViewById(R.id.tvMainShowDbData);
        Button btMainInsertData=findViewById(R.id.btMainInsertData);
        RxView.clicks(btMainInsertData).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                vm.insertLsBnPerson();
            }
        });
        Button btMainDeleteData=findViewById(R.id.btMainDeleteData);
        RxView.clicks(btMainDeleteData).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                String str=tvMainShowDbData.getText().toString();
                List<DbPerson> lsBn=Arrays.asList(new Gson().fromJson(str, DbPerson[].class));
                lsBn.remove(0);
//                List<BnPerson> lsBn= new ArrayList<>();
//                BnPerson bn=new BnPerson();
//                bn.setId(118L);
//                lsBn.add(bn);
                vm.deleteLsBnPerson(lsBn);
            }
        });

        // 数据变动监听
        vm.getDbLsBnPerson().observe(this, new Observer<List<DbPerson>>() {
            @Override
            public void onChanged(@Nullable List<DbPerson> lsBnPeople) {
                String strTemp=new Gson().toJson(lsBnPeople);
                tvMainShowDbData.setText(strTemp);
            }
        });
    }


}
