package com.dazhi.libroot.inte

import android.content.DialogInterface
import androidx.annotation.StringRes

/**
 * 功能：用于为Activity提供基础函数
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/11 16:18
 * 修改日期：2018/4/11 16:18
 */
interface IRootView {
    /*加载框显示*/
    fun loadingShow(msg: String?=null)
    fun loadingShow(@StringRes intStrId: Int)

    /*加载框销毁*/
    fun loadingShut()

    /*=======================================
     * 作者：WangZezhi  (2019-12-28  11:02)
     * 功能：简单消息窗口显示(不推荐，改为：@link{}方式调用)
     * 描述：这种实现方式局限性太高，改为链式调用方式
     *=======================================*/
    fun msgBoxShow(msg: String, title: String?=null,
                   strEsc: String?=null, onClickListenerEsc: DialogInterface.OnClickListener?=null,
                   strEnt: String?=null, onClickListenerEnt: DialogInterface.OnClickListener?=null)
}