package com.dazhi.libroot.inte;

import android.content.DialogInterface;
import android.support.annotation.StringRes;

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/11 16:18
 * 修改日期：2018/4/11 16:18
 */
public interface InteRootView {

    /*加载框显示*/
    void loadingShow(String msg);
    void loadingShow(@StringRes int intStrId);
    /*加载框销毁*/
    void loadingShut();


    /*简单消息窗口显示*/
    void msgBoxShow(String msg);
    void msgBoxShow(String title, String msg);
    void msgBoxShow(String msg, String strEnt, DialogInterface.OnClickListener onClickListener);
    void msgBoxShow(String title, String msg, String strEnt, DialogInterface.OnClickListener onClickListener);
    void msgBoxShow(String title, String msg, String strEsc, String strEnt, DialogInterface.OnClickListener onClickListener);
    void msgBoxShow(String title, String msg, String strEsc, DialogInterface.OnClickListener onClickListenerEsc, String strEnt, DialogInterface.OnClickListener onClickListenerEnt);
}
