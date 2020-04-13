package com.dazhi.libroot.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    public static void init(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        UtThread.corePoolSize = corePoolSize;
        UtThread.maximumPoolSize = maximumPoolSize;
        UtThread.keepAliveTime = keepAliveTime;
    }
    public static UtThread self() {
        return ClassHolder.INSTANCE;
    }


    public void execute(Runnable runnable){
        THREAD_POOL_EXECUTOR.execute(runnable);
    }

    public boolean isShutdown() {
        return THREAD_POOL_EXECUTOR.isShutdown();
    }

    public void shutdown(){
        THREAD_POOL_EXECUTOR.shutdown();
    }

    public void shutdownNow(){
        THREAD_POOL_EXECUTOR.shutdownNow();
    }

}
