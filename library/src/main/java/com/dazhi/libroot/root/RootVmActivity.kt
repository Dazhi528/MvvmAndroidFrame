package com.dazhi.libroot.root

import com.dazhi.libroot.inte.IRootView

/**
 * 功能：mvvm架构的Activity超类
 * 描述：这个类里的东西全部在UI线程
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/19 14:16
 * 修改日期：2018/4/19 14:16
 */
abstract class RootVmActivity<T : RootViewModel<IRootView>> : RootActivity() {
    protected var vm: T? = null
    override fun initViewAndDataAndEvent() {
        if (vm == null) {
            throw RuntimeException("Failure to instantiate VM!")
        }
        if (vm!!.checkCoroutineScope()) {
            throw RuntimeException("Failure to instantiate CoroutineScope!")
        }
        vm!!.attachUiView(this)
    }
}