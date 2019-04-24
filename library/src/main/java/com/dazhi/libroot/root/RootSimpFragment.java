package com.dazhi.libroot.root;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 功能：fragment基类
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/3/23 17:13
 * 修改日期：2018/3/23 17:13
 */
public abstract class RootSimpFragment extends RootFragment {
    //每一个p都去创建本地的CompositeDisposable，从而是否资源时，不相互影响
    private CompositeDisposable compositeDisposable;

    protected void initConfig(){ }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable=null;
        }
    }




}


