package com.dazhi.libroot.root

import android.content.Context
import androidx.startup.Initializer
import com.dazhi.libroot.util.RtCmn
import java.util.*

/**
 * 功能：用于应用启动初始化
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2020/11/16 18:47
 */
internal class RootInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        RtCmn.initApp(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return arrayListOf()
    }

}