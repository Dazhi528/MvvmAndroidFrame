package com.dazhi.sample

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.dazhi.libroot.root.RootVmActivity
import com.dazhi.libroot.util.RtCmn
import com.dazhi.libroot.util.RtLog
import com.dazhi.libroot.util.viewClick
import com.dazhi.sample.databinding.ActivitySampleBinding
import com.dazhi.sample.db.DbPerson
import java.util.*

class SampleActivity : RootVmActivity<VmMain, ActivitySampleBinding>() {

    override fun initBinding(): ActivitySampleBinding {
        return ActivitySampleBinding.inflate(layoutInflater)
    }

    override fun initVm(): VmMain {
        return ViewModelProvider(this).get(VmMain::class.java)
    }

    override fun initConfig(tvToolTitle: TextView?) {
        super.initConfig(tvToolTitle)
        // 设置标题
        tvToolTitle?.text = "我的第一个MVVM架构"
    }

    @SuppressLint("CheckResult")
    override fun initViewAndDataAndEvent() {
        super.initViewAndDataAndEvent()
        // UI
//        var lastTime=System.currentTimeMillis()
        viewClick(binding.btMainInsertData) {
            // 测试防抖动
//            val temp = System.currentTimeMillis()
//            RtLog.d("间隔：${temp-lastTime}")
//            lastTime = temp
//            return@viewClick
            //
            RtCmn.toastShort("测试插入")
            RtLog.e("测试插入：${ARouter.debuggable()}")
            vm!!.insertLsBnPerson()
        }

        viewClick(binding.btMainDeleteData) {
            val str = binding.tvMainShowDbData.text.toString()
            val lsBnPerson: MutableList<DbPerson> = ArrayList()
            for (i in 1..10) {
                lsBnPerson.add(DbPerson("name0$i"))
            }
            vm!!.deleteLsBnPerson(lsBnPerson);
        }
        // 数据变动监听
        vm!!.dbLsBnPerson.observe(this) { mList ->
            binding.tvMainShowDbData.setText(mList.map {
                "名称：${it.strName}"
            }.toString());
        }
    }

}