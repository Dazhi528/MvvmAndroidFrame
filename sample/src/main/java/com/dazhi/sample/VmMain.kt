package com.dazhi.sample

import com.dazhi.libroot.inte.IRootView
import com.dazhi.libroot.root.RootViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 功能：Room框架数据库操作实例
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/12/8 14:57
 * 修改日期：2018/12/8 14:57
 */
class VmMain : RootViewModel<IRootView>() {
    // 用于观察数据库所有消息变动
//    val dbLsBnPerson = MutableLiveData<List<DbPerson>>()

    /**=======================================
     * 作者：WangZezhi  (2018/12/8  16:02)
     * 功能：批量插入
     * 描述：
     * ======================================= */
    fun insertLsBnPerson() {
        // 测试数据
//        val lsBnPerson: MutableList<DbPerson> = ArrayList()
//        for (i in 1..10) {
////            lsBnPerson.add(DbPerson("name0$i"))
//        }
        // 数据库操作
        MainScope().launch {
            // 切换到IO线程
            withContext(Dispatchers.IO) {
                // 插入测试数据
//                FactoryDaoPerson.self().daoPerson.dbInsertLsBnPerson(lsBnPerson)
//                // 更新测试数据
//                dbLsBnPerson.postValue(FactoryDaoPerson.self().daoPerson.dbGetAllBnPerson())
            }
        }
    }

//    fun deleteLsBnPerson(lsBnPerson: List<DbPerson?>?) {
//        MainScope().launch {
//            withContext(Dispatchers.IO) {
//                FactoryDaoPerson.self().daoPerson.dbDeleteLsBnPerson(lsBnPerson)
//                // 更新测试数据
//                dbLsBnPerson.postValue(FactoryDaoPerson.self().daoPerson.dbGetAllBnPerson())
//            }
//        }
//    }
}