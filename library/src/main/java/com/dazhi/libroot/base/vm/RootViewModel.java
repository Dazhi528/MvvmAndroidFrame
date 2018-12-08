package com.dazhi.libroot.base.vm;

import android.arch.lifecycle.ViewModel;
import com.dazhi.libroot.base.inte.InteRootView;
import com.dazhi.libroot.base.inte.InteRootVm;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 功能：MVVM架构设计是的根VM抽象类
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/10/12 15:44
 * 修改日期：2018/10/12 15:44
 */
public abstract class RootViewModel<T extends InteRootView> extends ViewModel implements InteRootVm<T> {
    protected T vmView;
    private CompositeDisposable compositeDisposable;

    /**=======================================
     * 作者：WangZezhi  (2018/7/2  11:45)
     * 功能：本方法用于统一管理Rx事件
     * 描述：
     *=======================================*/
    protected void rxAdd(Disposable subscription) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(subscription);
    }

    private void rxClear() {
        if (compositeDisposable == null) {
            return;
        }
        compositeDisposable.clear();
    }

    @Override
    public void attachVmView(T vmView) {
        this.vmView = vmView;
    }

    @Override
    public void detachVmView() {
        this.vmView = null;
        rxClear();
    }


}
