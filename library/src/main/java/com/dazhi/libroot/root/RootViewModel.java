package com.dazhi.libroot.root;

import android.arch.lifecycle.ViewModel;
import com.dazhi.libroot.inte.InteRootView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 功能：MVVM架构设计是的根VM抽象类
 * 描述：这个类里的东西全部在工作线程
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/10/12 15:44
 * 修改日期：2018/10/12 15:44
 */
public abstract class RootViewModel<T extends InteRootView> extends ViewModel {
    protected T uiView;
    //每一个vm都去创建本地的CompositeDisposable，从而释放资源时，不相互影响
    private CompositeDisposable compositeDisposable;

    protected void attachUiView(T uiView) {
        this.uiView=uiView;
    }

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


    /**=======================================
     * 作者：WangZezhi  (2018/10/12  15:46)
     * 功能：重写ViewModel的清除方法
     * 描述：
     *=======================================*/
    @Override
    protected void onCleared() {
        this.uiView = null;
        //
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable=null;
        }
    }


}
