package com.dazhi.libroot.root

import androidx.viewbinding.ViewBinding

/**
 * 功能：fragment基类
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/3/23 17:13
 * 修改日期：2018/3/23 17:13
 */
abstract class RootSimpFragment<T : ViewBinding>: RootFragment<T>() {
    override fun initConfig() {}
}