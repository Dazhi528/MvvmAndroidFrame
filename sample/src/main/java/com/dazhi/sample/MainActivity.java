package com.dazhi.sample;

import com.dazhi.libroot.base.activity.RootVmActivity;
import com.dazhi.libroot.base.vm.RootViewModel;

public class MainActivity extends RootVmActivity<RootViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initConfig() {

    }


}
