package com.dazhi.libroot.root;

/**
 * 功能：mvvm架构的Activity超类
 * 描述：这个类里的东西全部在UI线程
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/19 14:16
 * 修改日期：2018/4/19 14:16
 */
public abstract class RootVmActivity<T extends RootViewModel> extends RootActivity {
    protected T vm;

    @Override
    protected void initViewAndDataAndEvent() {
        if(vm==null){
            throw new RuntimeException("Failure to instantiate VM!");
        }
        vm.attachUiView(this);
    }

}
