package com.dazhi.sample;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dazhi.libroot.base.activity.RootVmActivity;
import com.dazhi.sample.db.BnPerson;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

public class MainActivity extends RootVmActivity<VmMain> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initConfig() {
        // 初始化ViewModel
        vm=ViewModelProviders.of(this).get(VmMain.class);
        // 设置标题
        //配置标题
        TextView tvToolTitle = findViewById(R.id.librootToolbarTitle);
        tvToolTitle.setText("我的第一个MVVM架构");
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
                List<BnPerson> lsBn=JSON.parseArray(str, BnPerson.class);
                lsBn.remove(0);
//                List<BnPerson> lsBn= new ArrayList<>();
//                BnPerson bn=new BnPerson();
//                bn.setId(118L);
//                lsBn.add(bn);
                vm.deleteLsBnPerson(lsBn);
            }
        });

        // 数据变动监听
        vm.getDbLsBnPerson().observe(this, new Observer<List<BnPerson>>() {
            @Override
            public void onChanged(@Nullable List<BnPerson> lsBnPeople) {
                String strTemp=JSON.toJSONString(lsBnPeople);
                tvMainShowDbData.setText(strTemp);
            }
        });
    }


}
