package com.dazhi.sample

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dazhi.libroot.root.RootVmActivity
import com.dazhi.libroot.util.viewClick
import com.dazhi.sample.db.DbPerson
import kotlinx.android.synthetic.main.activity_sample.*
import kotlinx.coroutines.MainScope
import java.util.ArrayList

class SampleActivity : RootVmActivity<VmMain>() {
    override val layoutId: Int
        get() = R.layout.activity_sample

    override fun initConfig(tvToolTitle: TextView?) {
        // 初始化ViewModel
        vm = ViewModelProvider(this).get(VmMain::class.java)
        vm!!.initCoroutineScope(MainScope())
        // 设置标题
        tvToolTitle?.text = "我的第一个MVVM架构"
    }

    @SuppressLint("CheckResult")
    override fun initViewAndDataAndEvent() {
        super.initViewAndDataAndEvent()
        // UI
        viewClick(btMainInsertData) { vm!!.insertLsBnPerson() }

        viewClick(btMainDeleteData) {
            val str = tvMainShowDbData.text.toString()
            val lsBnPerson: MutableList<DbPerson> = ArrayList()
            for (i in 1..10) {
                lsBnPerson.add(DbPerson("name0$i"))
            }
            vm!!.deleteLsBnPerson(lsBnPerson);
        }
        // 数据变动监听
        vm!!.dbLsBnPerson.observe(this) { mList ->
            tvMainShowDbData.setText(mList.map {
                "名称：${it.strName}"
            }.toString());
        }
    }

}