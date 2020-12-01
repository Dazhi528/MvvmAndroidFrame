package com.dazhi.libroot.util

import java.util.concurrent.*

/**
 * 功能：线程池工具
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/9/17 14:11
 * 修改日期：2018/9/17 14:11
 */
object RtThread {
    // 如果不调用初始化,默认是newCachedThreadPool配置
    private var corePoolSize = 0
    private var maximumPoolSize = Int.MAX_VALUE
    private var keepAliveTime: Long = 60000 // 默认保活1分钟
    private var blockingQueue: BlockingQueue<Runnable> = SynchronousQueue()
    private var THREAD_POOL_EXECUTOR: ExecutorService? = null

    @JvmStatic
    @Synchronized
    fun init(corePoolSize: Int, maximumPoolSize: Int,
             keepAliveTime: Long,
             blockingQueue: BlockingQueue<Runnable>) {
        require(corePoolSize>=0 && maximumPoolSize>0 && keepAliveTime>=0) {
            "Parameter setting error"
        }
        //
        this.corePoolSize = corePoolSize
        this.maximumPoolSize = maximumPoolSize
        this.keepAliveTime = keepAliveTime
        this.blockingQueue = blockingQueue
        //
        if (THREAD_POOL_EXECUTOR != null) {
            THREAD_POOL_EXECUTOR!!.shutdownNow()
            THREAD_POOL_EXECUTOR = null
        }
    }

    @JvmStatic
    fun execute(runnable: Runnable?) {
        if (THREAD_POOL_EXECUTOR == null) {
            synchronized(RtThread::class.java) {
                if (THREAD_POOL_EXECUTOR == null) {
                    THREAD_POOL_EXECUTOR = ThreadPoolExecutor(corePoolSize,
                            maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                            blockingQueue)
                }
            }
        }
        THREAD_POOL_EXECUTOR!!.execute(runnable)
    }

    @JvmStatic
    val isShutdown: Boolean
        get() = THREAD_POOL_EXECUTOR?.isShutdown == true

    @JvmStatic
    fun shutdown() {
        THREAD_POOL_EXECUTOR?.shutdown()
    }

    @JvmStatic
    fun shutdownNow() {
        THREAD_POOL_EXECUTOR?.shutdownNow()
    }

}