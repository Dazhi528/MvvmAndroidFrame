package com.dazhi.libroot.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 功能：线程池工具
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/9/17 14:11
 * 修改日期：2018/9/17 14:11
 */
public class UtThread {
    private static final ExecutorService SINGLE_THREAD_EXECUTOR = Executors.newSingleThreadExecutor();

    public static void runSingleThread(Runnable runnable){
        SINGLE_THREAD_EXECUTOR.execute(runnable);
    }

    public static void shutdownNow(){
        SINGLE_THREAD_EXECUTOR.shutdownNow();
    }


}
