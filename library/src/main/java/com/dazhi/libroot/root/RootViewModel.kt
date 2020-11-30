package com.dazhi.libroot.root

import androidx.lifecycle.ViewModel
import com.dazhi.libroot.inte.IRootView

/**
 * 功能：MVVM架构设计是的根VM抽象类
 * 描述：这个类里的东西全部在工作线程
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/10/12 15:44
 * 修改日期：2018/10/12 15:44
 */
abstract class RootViewModel<T : IRootView>: ViewModel() {
    @Suppress("MemberVisibilityCanBePrivate")
    protected var uiView: T? = null

    fun attachUiView(uiView: T) {
        this.uiView = uiView
    }

    /**=======================================
     * 作者：WangZezhi  (2018/10/12  15:46)
     * 功能：重写ViewModel的清除方法
     * 描述：
     * ======================================= */
    override fun onCleared() {
        uiView = null
    }

}