package com.dazhi.libroot.root;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 功能：简单的Activity超类
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/19 14:16
 * 修改日期：2018/4/19 14:16
 */
public abstract class RootSimpActivity extends RootActivity {
    //每一个p都去创建本地的CompositeDisposable，从而释放资源时，不相互影响
    private CompositeDisposable compositeDisposable;

    /**=======================================
     * 作者：WangZezhi  (2018/7/2  11:45)
     * 功能：本方法用于统一管理Rx事件
     * 描述：
     *=======================================*/
    protected void rxAdd(Disposable subscription) {
        if(subscription==null){
            return;
        }
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(subscription);
    }

    @Override
    protected void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable=null;
        }
        //
        super.onDestroy();
    }


}
