package com.dazhi.sample.db;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
    // 查询所有数据
    @Query("SELECT * FROM tab_person")
    List<DbPerson> dbGetAllBnPerson();

    // 批量插入
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void dbInsertLsBnPerson(List<DbPerson> lsBnPerson);

    // 批量删除
    @Delete
    void dbDeleteLsBnPerson(List<DbPerson> lsBnPerson);

}
