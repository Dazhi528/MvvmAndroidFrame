package com.dazhi.libroot.root;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Window;
import com.dazhi.libroot.R;

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/11 15:32
 * 修改日期：2018/4/11 15:32
 */
public abstract class RootDialog extends AppCompatDialog {

    protected RootDialog(Context context) {
        super(context, R.style.LibRootDialogBaseStyle);
        setContentView(initLayout());
        Window window=getWindow();
        if(window!=null){
            window.setWindowAnimations(R.style.LibRootDialogAnimScale);
        }
    }

    protected abstract int initLayout();


}
