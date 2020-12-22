package com.dazhi.sample

import android.widget.TextView
import com.dazhi.libroot.root.RootSimpActivity
import com.dazhi.sample.databinding.ActivitySimpleBinding

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2020/12/22 09:46
 */
class SimpleActivity: RootSimpActivity<ActivitySimpleBinding>() {
    override fun initBinding(): ActivitySimpleBinding {
        return ActivitySimpleBinding.inflate(layoutInflater)
    }

    override fun initConfig(tvToolTitle: TextView?) {
        tvToolTitle?.text = "RootSimpActivity"
    }

    override fun initViewAndDataAndEvent() {

    }

}