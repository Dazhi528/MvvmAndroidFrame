package com.dazhi.sample;

import com.dazhi.libroot.root.RootApp;
import com.dazhi.libroot.util.RtLog;

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
        // ARouter
//        if(BuildConfig.DEBUG) {
//            ARouter.openLog();  // 打印日志
//            ARouter.openDebug(); // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }
//        ARouter.init((Application) RtCmn.getAppContext()); // 尽可能早，推荐在Application中初始化
        //
        RtLog.setOpen();
        // 友盟统计(没有推送，最后一个值填null即可)
        // UMConfigure.init(Context context, String appkey, String channel, int deviceType, String pushSecret);
//        UMConfigure.init(this, "5e070e85570df387c2000353", "MvvmAndroidLib",
//                UMConfigure.DEVICE_TYPE_PHONE, null);
//        MobclickAgent.setCatchUncaughtExceptions(true);
//        UtConfig.self().initEngineLifecycle(new EngineLifecycleApp());
        //
//        FactoryDaoPerson.self().init(AppDatabase.self(this).getDaoPerson());
    }


}
