package com.dazhi.sample.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * 功能：room 框架 数据库构建类
 * 描述：
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：2018/12/8 15:29
 * 修改日期：2018/12/8 15:29
 */
@Database(entities = {BnPerson.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoPerson getDaoPerson();

    /**=======================================
     * 作者：WangZezhi  (2018/12/8  15:44)
     * 功能：单列获得实例方法
     * 描述：
     *=======================================*/
    private static AppDatabase instance = null;
    public static synchronized AppDatabase self(Context context) {
        if (instance == null) {
            instance = buildDatabase(context);
        }
        return instance;
    }
    private static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "intelink_app.db")
                .build();
    }


}
