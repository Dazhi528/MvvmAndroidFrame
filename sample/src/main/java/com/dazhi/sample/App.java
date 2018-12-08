package com.dazhi.sample;

import com.dazhi.libroot.base.RootApp;
import com.dazhi.sample.db.AppDatabase;
import com.dazhi.sample.db.FactoryDaoPerson;

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/12/8 15:15
 * 修改日期：2018/12/8 15:15
 */
public class App extends RootApp {

    @Override
    public void onCreate() {
        super.onCreate();
        //
        FactoryDaoPerson.self().init(AppDatabase.self(this).getDaoPerson());
    }


}
