package com.dazhi.libroot.root

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.dazhi.libroot.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/11 15:32
 * 修改日期：2018/4/11 15:32
 */
abstract class RootDialog protected constructor(context: Context?, var mCoroutineScope: CoroutineScope? = null) : AppCompatDialog(context, R.style.LibRootDialogBaseStyle) {
    protected abstract val layoutId: Int
    protected abstract fun initViewAndDataAndEvent()

    init {
        window?.setWindowAnimations(R.style.LibRootDialogAnimScale)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        setContentView(layoutId)
        initViewAndDataAndEvent()
    }

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

    override fun dismiss() {
        mCoroutineScope?.cancel()
        super.dismiss()
    }

}