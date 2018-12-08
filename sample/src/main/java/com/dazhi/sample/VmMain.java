package com.dazhi.sample;

import com.dazhi.libroot.base.vm.RootViewModel;
import com.dazhi.sample.db.BnPerson;
import com.dazhi.sample.db.FactoryDaoPerson;
import java.util.List;

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/12/8 14:57
 * 修改日期：2018/12/8 14:57
 */
public class VmMain extends RootViewModel<MainActivity> {

    /**=======================================
     * 作者：WangZezhi  (2018/12/8  16:02)
     * 功能：批量插入
     * 描述：
     *=======================================*/
    public void insertLsBnPerson(List<BnPerson> lsBnPerson){
        FactoryDaoPerson.self().getDaoPerson().insertLsBnPerson(lsBnPerson);
    }


}
