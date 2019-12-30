package com.dazhi.libroot.util;

import com.dazhi.libroot.inte.InteRootEngineLifecycle;

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2019-12-30 11:44
 * 修改日期：2019-12-30 11:44
 */
public class UtConfig {
    private InteRootEngineLifecycle inteRootEngineLifecycle;

    private UtConfig(){}
    private static final class ClassHolder {
        static final UtConfig INSTANCE = new UtConfig();
    }

    public static UtConfig self(){
        return ClassHolder.INSTANCE;
    }

    /*=======================================
     * 作者：WangZezhi  (2019-12-30  11:50)
     * 功能：生命周期引擎配置
     * 描述：
     *=======================================*/
    public synchronized void initEngineLifecycle(InteRootEngineLifecycle inteRootEngineLifecycle) {
        this.inteRootEngineLifecycle = inteRootEngineLifecycle;
    }

    public InteRootEngineLifecycle getEngineLifecycle() {
        return inteRootEngineLifecycle;
    }

}
