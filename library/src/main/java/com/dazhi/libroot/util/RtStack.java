package com.dazhi.libroot.util;

import android.app.Activity;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.LinkedList;

/**
 * 作者：WangZezhi  (17-9-6  上午11:41)
 * 功能：Activity堆栈
 * 详情：用于管理所有的Acitivity
 */
@SuppressWarnings("unused")
public class RtStack {
    //存activity的list，方便管理activity
    private final LinkedList<Activity> lkActivity;

    private RtStack() {
        lkActivity = new LinkedList<>();
    }
    //
    private static final class holderClass {
        static final RtStack INSTANCE=new RtStack();
    }

    public static RtStack self(){
        return holderClass.INSTANCE;
    }

    /**
     * 将Activity添加到activityList中
     */
    public void addActivity(Activity activity){
        lkActivity.add(activity);
    }

    /**
     * 将Activity移除
     */
    public void removeActivity(Activity activity){
        if(null != activity){
            lkActivity.remove(activity);
        }
    }

    /**
     * 获取栈顶Activity
     */
    public Activity getLastActivity(){
        if(lkActivity==null || lkActivity.size()==0){
            return null;
        }
        return lkActivity.getLast();
    }

    public Activity getActivityByName(String className){
        if (className != null) {
            for (Activity activity : lkActivity) {
                if (activity.getClass().getName().equals(className)) {
                    return activity;
                }
            }
        }
        return null;
    }

    /**
     * 判断某一Activity是否在运行
     */
    public boolean booRunnActivity(String className) {
        if (className != null) {
            for (Activity activity : lkActivity) {
                if (activity.getClass().getName().equals(className)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        try {
            ARouter.getInstance().destroy();
            // 关闭工具的线程池
            RtThread.shutdownNow();
            // 关闭数据库
            //DbHelper.self().closeDbForce();
            // 关闭所有Activity
            finishAllActivity();
            //杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出所有的Activity
     */
    public void finishAllActivity(){
        for(Activity activity : lkActivity){
            if(null != activity){
                activity.finish();
            }
        }
    }

}
