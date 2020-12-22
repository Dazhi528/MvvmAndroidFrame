package com.dazhi.sample

import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.dazhi.libroot.root.RootSimpActivity
import com.dazhi.sample.databinding.ActivityArouterBinding

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2020/12/22 09:46
 */
@Route(path = "/test/ARouterActivity")
class ARouterActivity: RootSimpActivity<ActivityArouterBinding>() {
    override fun initBinding(): ActivityArouterBinding {
        return ActivityArouterBinding.inflate(layoutInflater)
    }

    override fun initConfig(tvToolTitle: TextView?) {
        tvToolTitle?.text = "路由导航测试"
    }

    override fun initViewAndDataAndEvent() {

    }

}