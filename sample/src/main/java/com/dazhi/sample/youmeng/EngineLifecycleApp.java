package com.dazhi.sample.youmeng;

import android.content.Context;
import com.dazhi.libroot.inte.InteRootEngineLifecycle;
import com.umeng.analytics.MobclickAgent;

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2019-12-28 15:39
 * 修改日期：2019-12-28 15:39
 */
public class EngineLifecycleApp implements InteRootEngineLifecycle {

    @Override
    public void onCreateRoot(Context context) {
    }

    @Override
    public void onResumeRoot(Context context) {
        // 友盟统计
        MobclickAgent.onResume(context);
    }

    @Override
    public void onPauseRoot(Context context) {
        // 友盟统计
        MobclickAgent.onPause(context);
    }

    @Override
    public void onStopRoot(Context context) {
    }
    @Override
    public void onDestroyRoot(Context context) {
    }

}
