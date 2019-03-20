package com.dazhi.libroot.rx;

import android.text.TextUtils;

import com.dazhi.libroot.R;
import com.dazhi.libroot.inte.InteRootView;
import com.dazhi.libroot.util.UtLog;
import com.dazhi.libroot.util.UtRoot;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * 功能：自定义观察者
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/8 09:13
 * 修改日期：2018/4/8 09:13
 */
public abstract class SubscriberCustom<T> extends ResourceSubscriber<T> {
    private InteRootView inteRootView;

    public SubscriberCustom(InteRootView inteRootView){
        this.inteRootView=inteRootView;
    }

    public abstract void next(T t);

    @Override
    public void onNext(T t) {
        try {
            next(t);
            onComplete();
        }catch (Exception e){
            onError(e);
        }
    }

    @Override
    public void onComplete() {
        if(inteRootView!=null){
            inteRootView.loadingShut();
        }
    }

    @Override
    public void onError(Throwable t) {
        if(inteRootView!=null){
            inteRootView.loadingShut();
        }
        if (t == null || TextUtils.isEmpty(t.getMessage())) {
            String strNull= UtRoot.getString(R.string.web_rsps_null);
            strNull=TextUtils.isEmpty(strNull)?"":strNull;
            UtRoot.toastShort(strNull);
            UtLog.e(strNull);
            return;
        }
        String strError=t.getMessage();
        strError=TextUtils.isEmpty(strError)?"":strError;
        UtRoot.toastShort(strError);
        UtLog.e(strError);
    }


}
