package com.dazhi.libroot.util

import android.view.View

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 日期：20-9-14 上午11:27
 */

/**======================================
 * 作者：WangZezhi  (20-9-15  下午4:03)
 * 功能：视图点击防抖
 * 描述：
 *=======================================*/
fun viewClick(view: View, mDuration: Long=1000, mClick: ()->Unit) {
   view.setOnClickListener(ThrottleOnClickListener(mDuration, mClick))
}
// 防抖监听实现
private class ThrottleOnClickListener(private val mDuration: Long, private val mClick: ()->Unit): View.OnClickListener {
    private var available=true // 点击是否有效
    // 延时时间到了，置位
    private val mRunnable = Runnable {
        available = true
    }

    override fun onClick(mView: View?) {
        if(available) {
            available=false
            mClick()
            mView?.postDelayed(mRunnable, mDuration)
        }
    }
}
