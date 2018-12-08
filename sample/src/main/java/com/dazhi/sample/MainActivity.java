package com.dazhi.sample;

import android.arch.lifecycle.ViewModelProviders;
import android.widget.TextView;
import com.dazhi.libroot.base.activity.RootVmActivity;

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


}
