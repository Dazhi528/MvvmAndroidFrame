package com.dazhi.libroot.root

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * 功能：fragment基类
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/3/23 17:13
 * 修改日期：2018/3/23 17:13
 */
abstract class RootSimpFragment protected constructor(var mCoroutineScope: CoroutineScope? = null) : RootFragment() {
    override fun initConfig() {}

    /**=======================================
     * 作者：WangZezhi  (2018/7/2  11:45)
     * 功能：本方法用于统一管理Rx事件
     * 描述：
     * ======================================= */
    protected fun scopeLaunch(call: () -> Unit) {
        mCoroutineScope?.launch {
            call()
        }
    }

    override fun onDestroy() {
        mCoroutineScope?.cancel()
        //
        super.onDestroy()
    }
}