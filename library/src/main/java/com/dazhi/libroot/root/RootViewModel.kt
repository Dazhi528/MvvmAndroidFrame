package com.dazhi.libroot.root

import androidx.lifecycle.ViewModel
import com.dazhi.libroot.inte.IRootView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * 功能：MVVM架构设计是的根VM抽象类
 * 描述：这个类里的东西全部在工作线程
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/10/12 15:44
 * 修改日期：2018/10/12 15:44
 */
abstract class RootViewModel<T : IRootView>: ViewModel() {
    protected var uiView: T? = null
    private var mCoroutineScope: CoroutineScope? = null
    private var boInitCoroutineScope = false

    fun attachUiView(uiView: T) {
        this.uiView = uiView
    }
    fun initCoroutineScope(mCoroutineScope: CoroutineScope) {
        this.mCoroutineScope = mCoroutineScope
        boInitCoroutineScope = true
    }
    internal fun checkCoroutineScope(): Boolean {
        return boInitCoroutineScope && mCoroutineScope==null
    }

    /**=======================================
     * 作者：WangZezhi  (2018/7/2  11:45)
     * 功能：本方法用于协程中运行业务逻辑
     * 描述：
     * ======================================= */
    protected fun scopeLaunch(call: suspend () -> Unit) {
        mCoroutineScope?.launch {
            call()
        }
    }

    /**=======================================
     * 作者：WangZezhi  (2018/10/12  15:46)
     * 功能：重写ViewModel的清除方法
     * 描述：
     * ======================================= */
    override fun onCleared() {
        mCoroutineScope?.cancel()
        mCoroutineScope = null
        uiView = null
    }

}