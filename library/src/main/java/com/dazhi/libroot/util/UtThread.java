package com.dazhi.libroot.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 功能：线程池工具
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/9/17 14:11
 * 修改日期：2018/9/17 14:11
 */
public class UtThread {
    // 如果不调用初始化,默认是newSingleThreadExecutor配置
    private static int corePoolSize=1;
    private static int maximumPoolSize=1;
    private static long keepAliveTime=0;
    private final ExecutorService THREAD_POOL_EXECUTOR;

    private UtThread() {
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }
    private static final class ClassHolder {
        static final UtThread INSTANCE = new UtThread();
    }
    private static UtThread self() {
        return ClassHolder.INSTANCE;
    }

    private void exec(Runnable runnable){
        THREAD_POOL_EXECUTOR.execute(runnable);
    }

    private boolean booSd() {
        return THREAD_POOL_EXECUTOR.isShutdown();
    }

    private void sd(){
        THREAD_POOL_EXECUTOR.shutdown();
    }

    private void sdNow(){
        THREAD_POOL_EXECUTOR.shutdownNow();
    }

    /*=======================================
     * 作者：WangZezhi  (2020/4/13  9:45)
     * 功能：对外出口
     * 描述：
     *=======================================*/
    public static void init(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        UtThread.corePoolSize = corePoolSize;
        UtThread.maximumPoolSize = maximumPoolSize;
        UtThread.keepAliveTime = keepAliveTime;
    }

    public static void execute(Runnable runnable) {
        UtThread.self().exec(runnable);
    }

    public static boolean isShutdown() {
        return UtThread.self().booSd();
    }

    public static void shutdown(){
        UtThread.self().sd();
    }

    public static void shutdownNow(){
        UtThread.self().sdNow();
    }

}
