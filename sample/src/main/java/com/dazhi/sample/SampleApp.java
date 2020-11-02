package com.dazhi.sample;

import com.dazhi.libroot.root.RootApp;
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
public class SampleApp extends RootApp {

    @Override
    public void onCreate() {
        super.onCreate();
        //
        // 友盟统计(没有推送，最后一个值填null即可)
        // UMConfigure.init(Context context, String appkey, String channel, int deviceType, String pushSecret);
//        UMConfigure.init(this, "5e070e85570df387c2000353", "MvvmAndroidLib",
//                UMConfigure.DEVICE_TYPE_PHONE, null);
//        MobclickAgent.setCatchUncaughtExceptions(true);
//        UtConfig.self().initEngineLifecycle(new EngineLifecycleApp());
        //
        FactoryDaoPerson.self().init(AppDatabase.self(this).getDaoPerson());
    }


}
