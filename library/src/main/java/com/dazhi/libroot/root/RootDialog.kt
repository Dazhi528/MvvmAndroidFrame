package com.dazhi.libroot.root

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import androidx.viewbinding.ViewBinding
import com.dazhi.libroot.R

/**
 * 功能：对话框基类
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/11 15:32
 * 修改日期：2018/4/11 15:32
 */
abstract class RootDialog<T : ViewBinding> protected constructor(context: Context?) : AppCompatDialog(context, R.style.LibRootDialogBaseStyle) {
    @Suppress("LeakingThis")
    private var _binding: T? = initBinding()
    protected val binding get() = _binding!!
    protected abstract fun initBinding(): T
    protected abstract fun initViewAndDataAndEvent()

    init {
        window?.setWindowAnimations(R.style.LibRootDialogAnimScale)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        setContentView(binding.root)
        initViewAndDataAndEvent()
    }

    override fun dismiss() {
        _binding = null
        super.dismiss()
    }

}