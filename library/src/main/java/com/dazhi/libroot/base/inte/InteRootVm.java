package com.dazhi.libroot.base.inte;

/**
 * 功能：RootViewModel需要实现本接口用于回调
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/19 14:05
 * 修改日期：2018/4/19 14:05
 */
public interface InteRootVm<T extends InteRootView> {
    void attachVmView(T vmView);
    void detachVmView();
}
