package com.dazhi.libroot.util;

import android.content.Context;
import android.content.Intent;

/**
 * 功能：程序崩溃处理器
 * 描述：用法--App里调用UtCrashHandler.self()即可
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/10/13 09:33
 * 修改日期：2018/10/13 09:33
 */
public class RtCatch implements Thread.UncaughtExceptionHandler {
    private static boolean restart=false; // 默认不重启

    private RtCatch(){
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    private static final class HolderClass {
        static final RtCatch INSTANCE=new RtCatch();
    }
    public static RtCatch open(boolean booRestart) {
        restart=booRestart;
        return HolderClass.INSTANCE;
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        // 打印异常
        RtLog.e(e.getMessage());
        // 抓到异常后自动重启App
        Context app = RtCmn.getAppContext();
        Intent intent = app.getPackageManager().getLaunchIntentForPackage(app.getPackageName());
        if(intent!=null){
            // 重启
            if(restart) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                app.startActivity(intent);
            }
            // 关闭所有Activity
            RtStack.self().exitApp();
        }
    }


}
