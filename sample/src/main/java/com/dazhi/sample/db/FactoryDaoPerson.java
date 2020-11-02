package com.dazhi.sample.db;

/**
 * 功能：用于获得Dao对象的工厂类
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/12/8 15:52
 * 修改日期：2018/12/8 15:52
 */
public class FactoryDaoPerson {
    private DaoPerson daoPerson;

    public void init(DaoPerson daoPerson) {
        this.daoPerson = daoPerson;
    }

    public DaoPerson getDaoPerson() {
        return daoPerson;
    }

    /**=======================================
     * 作者：WangZezhi  (2018/12/8  15:54)
     * 功能：单例
     * 描述：
     *=======================================*/
    private FactoryDaoPerson(){
    }
    private static final class HolderClass {
        static final FactoryDaoPerson INSTANCE = new FactoryDaoPerson();
    }
    public static FactoryDaoPerson self() {
        return HolderClass.INSTANCE;
    }


}
