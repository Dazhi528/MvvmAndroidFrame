package com.dazhi.libroot.ui.dialog

import android.content.Context
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import com.dazhi.libroot.R
import com.dazhi.libroot.databinding.LibrootDialogLoadBinding
import com.dazhi.libroot.root.RootDialog
import com.dazhi.libroot.util.RtCmn

/**
 * 功能：进度对话框
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/4/19 15:24
 * 修改日期：2018/4/19 15:24
 */
class DialogLoad(context: Context?, private val strMsg: String?) : RootDialog<LibrootDialogLoadBinding>(context) {
    // 旋转动画
    private var rotateAnimation: RotateAnimation? = null

    override fun initBinding(): LibrootDialogLoadBinding {
        return LibrootDialogLoadBinding.inflate(layoutInflater)
    }

    override fun initViewAndDataAndEvent() {
        setCancelable(false) // 禁用返回按钮销毁
        setCanceledOnTouchOutside(false) // 禁用触摸屏幕销毁
        val showText = if (strMsg.isNullOrEmpty()) {
            RtCmn.getString(R.string.libroot_loading)
        } else strMsg
        binding.tvDlogloadMsg.text = showText
        rotateAnimation = RotateAnimation(0F, 360F, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation?.interpolator = LinearInterpolator()
        rotateAnimation?.duration = 1000
        rotateAnimation?.repeatCount = -1
        binding.ivDlogloading.startAnimation(rotateAnimation)
    }

    override fun dismiss() {
        super.dismiss()
        rotateAnimation?.cancel()
    }

}