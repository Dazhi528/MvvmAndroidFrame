package com.dazhi.libroot.base.inte;

import android.content.DialogInterface;

/**
 * 功能：Activity需实现本接口，用于回调UI显示提示
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/11 16:18
 * 修改日期：2018/4/11 16:18
 */
public interface InteRootView {
    /*加载框显示*/
    void loadingShow(String msg);
    /*加载框销毁*/
    void loadingShut();


    /*简单消息窗口显示*/
    void msgBoxShow(String msg);
    /*简单消息窗口显示带按钮监听*/
    void msgBoxShow(String msg, DialogInterface.OnClickListener onClickListener);
    /*简单消息窗口显示带按钮监听*/
    void msgBoxShow(String msg, String strEsc, String strEnt, DialogInterface.OnClickListener onClickListener);
}
