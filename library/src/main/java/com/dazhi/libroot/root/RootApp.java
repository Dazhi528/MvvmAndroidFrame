package com.dazhi.libroot.root;

import android.content.res.Configuration;
import android.content.res.Resources;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dazhi.libroot.util.RtLog;
import com.dazhi.libroot.util.RtCmn;
import androidx.multidex.MultiDexApplication;

/**
 * 功能：分包超类App
 * 描述：可根据需要继承此类
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/3/1 14:31
 * 修改日期：2018/3/1 14:31
 */
public abstract class RootApp extends MultiDexApplication {

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
