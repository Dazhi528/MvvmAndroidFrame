package com.dazhi.libroot.root

import androidx.viewbinding.ViewBinding
import com.dazhi.libroot.inte.IRootView

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2019/2/27 14:27
 * 修改日期：2019/2/27 14:27
 */
abstract class RootVmFragment<T: RootViewModel<IRootView>, E: ViewBinding> : RootFragment<E>() {
    // vm=ViewModelProviders.of(activity).get(VmXXX.class);
    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var vm: T
    protected abstract fun initVm(): T

    override fun initConfig() {
        vm = initVm()
    }
    override fun initViewAndDataAndEvent() {
        vm.attachUiView(this)
    }
}