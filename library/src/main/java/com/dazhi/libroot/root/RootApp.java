package com.dazhi.libroot.root;

import android.content.res.Configuration;
import android.content.res.Resources;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dazhi.libroot.util.UtLog;
import com.dazhi.libroot.util.UtRoot;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import androidx.multidex.MultiDexApplication;

/**
 * 功能：分包超类App
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/3/1 14:31
 * 修改日期：2018/3/1 14:31
 */
public abstract class RootApp extends MultiDexApplication {
    // 用于配置打开日志等操作
    protected void initConfigBaseFront(){}

    @Override
    public void onCreate() {
        super.onCreate();
        initConfigBaseFront();
        UtRoot.initApp(this);
        ZXingLibrary.initDisplayOpinion(this);
        // ARouter
        if (UtLog.booDebug()) { // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();  // 打印日志
            ARouter.openDebug(); // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    @Override
    public Resources getResources() {
        // 字体不随系统字体改变
        Resources res = super.getResources();
        Configuration configuration = res.getConfiguration();
        if (configuration.fontScale != 1.0f) {
            configuration.fontScale = 1.0f;
            res.updateConfiguration(configuration, res.getDisplayMetrics());
        }
        return res;
    }


}
