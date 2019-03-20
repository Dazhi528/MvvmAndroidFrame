package com.dazhi.libroot.root;

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2019/2/27 14:27
 * 修改日期：2019/2/27 14:27
 */
public abstract class RootVmFragment<T extends RootViewModel> extends RootFragment {
    protected T vm; // vm=ViewModelProviders.of(activity).get(VmDebug.class);

    @Override
    protected void initViewAndDataAndEvent() {
        vm.attachUiView(this);
    }

}
