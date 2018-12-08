package com.dazhi.sample.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * 功能：表 tab_person 实体类
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/12/8 15:45
 * 修改日期：2018/12/8 15:45
 */
@Entity(tableName = "tab_person")
public class BnPerson {
    @PrimaryKey(autoGenerate = true)
    private Long id=0L; //数据库主键(数据库增加字段)

    @ColumnInfo(name = "name")
    private String strName="";



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }


}
