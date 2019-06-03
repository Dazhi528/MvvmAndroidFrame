package com.dazhi.libroot.root;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.Window;
import com.dazhi.libroot.R;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/11 15:32
 * 修改日期：2018/4/11 15:32
 */
public abstract class RootDialog extends AppCompatDialog {
    //每一个p都去创建本地的CompositeDisposable，从而释放资源时，不相互影响
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected RootDialog(Context context) {
        super(context, R.style.LibRootDialogBaseStyle);
        setContentView(initLayout());
        Window window=getWindow();
        if(window!=null){
            window.setWindowAnimations(R.style.LibRootDialogAnimScale);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        initViewAndDataAndEvent();
    }

    protected abstract int initLayout();
    protected void initViewAndDataAndEvent() {
    }


    /**=======================================
     * 作者：WangZezhi  (2018/7/2  11:45)
     * 功能：本方法用于统一管理Rx事件
     * 描述：
     *=======================================*/
    protected void rxAdd(Disposable disposable) {
        if (compositeDisposable == null || disposable==null) {
            return;
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void dismiss() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable=null;
        }
        //
        super.dismiss();
    }


}
