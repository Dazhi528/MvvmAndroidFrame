package com.dazhi.libroot.inte;

import android.content.Context;

/**
 * 功能：基类生命周期方法扩展引擎
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2019-12-28 10:59
 * 修改日期：2019-12-28 10:59
 */
public interface IRootEngineLifecycle {
    void onCreateRoot(Context context);
    void onResumeRoot(Context context);
    void onPauseRoot(Context context);
    void onStopRoot(Context context);
    void onDestroyRoot(Context context);
}
