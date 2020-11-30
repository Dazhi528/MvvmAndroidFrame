package com.dazhi.libroot.root

import android.widget.TextView
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * 功能：简单的Activity超类
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/19 14:16
 * 修改日期：2018/4/19 14:16
 */
abstract class RootSimpActivity<T : ViewBinding> : RootActivity<T>()