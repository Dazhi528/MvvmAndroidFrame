package com.dazhi.libroot.ui.dialog;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.dazhi.libroot.R;
import com.dazhi.libroot.root.RootDialog;

/**
 * 功能：进度对话框
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/19 15:24
 * 修改日期：2018/4/19 15:24
 */
public class DialogLoad extends RootDialog {
    private RotateAnimation rotateAnimation; //旋转动画

    @Override
    protected int initLayout() {
        return R.layout.libroot_dialog_load;
    }

    public DialogLoad(Context context, String strMsg) {
        super(context);
        //
        setCancelable(false); // 禁用返回按钮销毁
        setCanceledOnTouchOutside(false); // 禁用触摸屏幕销毁
        //
        TextView tvMsg = (TextView) findViewById(R.id.tvDlogloadMsg);
        if (tvMsg != null) {
            tvMsg.setText(strMsg);
        }
        //
        ImageView ivLoading = (ImageView) findViewById(R.id.ivDlogloading);
        if (ivLoading != null) {
            rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            rotateAnimation.setDuration(1000);
            rotateAnimation.setRepeatCount(-1);
            ivLoading.startAnimation(rotateAnimation);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (rotateAnimation != null) {
            rotateAnimation.cancel();
        }
    }


}
