package com.dazhi.libroot.util;

import com.dazhi.libroot.inte.IRootEngineLifecycle;

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2019-12-30 11:44
 * 修改日期：2019-12-30 11:44
 */
public class RtConfig {
    private IRootEngineLifecycle iRootEngineLifecycle;

    private RtConfig(){}
    private static final class ClassHolder {
        static final RtConfig INSTANCE = new RtConfig();
    }

    public static RtConfig self(){
        return ClassHolder.INSTANCE;
    }

    /*=======================================
     * 作者：WangZezhi  (2019-12-30  11:50)
     * 功能：生命周期引擎配置
     * 描述：
     *=======================================*/
    public synchronized void initEngineLifecycle(IRootEngineLifecycle iRootEngineLifecycle) {
        this.iRootEngineLifecycle = iRootEngineLifecycle;
    }

    public IRootEngineLifecycle getEngineLifecycle() {
        return iRootEngineLifecycle;
    }

}
