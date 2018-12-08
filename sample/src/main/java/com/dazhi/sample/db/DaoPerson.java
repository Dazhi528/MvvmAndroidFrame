package com.dazhi.sample.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import java.util.List;

/**
 * 功能：
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/12/8 15:50
 * 修改日期：2018/12/8 15:50
 */
@Dao
public interface DaoPerson {

    // 批量插入
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLsBnPerson(List<BnPerson> lsBnPerson);

}
