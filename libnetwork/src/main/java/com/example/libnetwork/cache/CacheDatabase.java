package com.example.libnetwork.cache;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.libcommon.AppGlobals;

/**
 * 缓存数据的数据库
 * author : Iwen大大怪
 * create : 2020/10/26 21:10
 */
@Database(entities = {Cache.class}, version = 1)
public abstract class CacheDatabase extends  RoomDatabase{

    private static final CacheDatabase database;

    static {
        // 创建一个内存数据库
        // 但是这种数据库的数据只存在于内存中，也就是进程被杀之后，数据随之丢失
        // Room.inMemoryDatabaseBuilder()
        database = Room.databaseBuilder(AppGlobals.getApplication(),CacheDatabase.class,"small_fish_cache")
                // 是否允许在主线程操作数据库
                .allowMainThreadQueries()
                // 数据库创建和打开后的回调
                //.addCallback()
                // 设置查询的线程池
                //.setQueryExecutor()
                //.openHelperFactory()
                // room的日志模式
                //.setJournalMode()
                // 数据库升级之后的回滚
                //.fallbackToDestructiveMigration()
                //数据库升级异常后根据指定版本进行回滚
                //.fallbackToDestructiveMigrationFrom()
                // .addMigrations(CacheDatabase.sMigration)
                .build();
    }
    public abstract CacheDao getCacheDao();

    public static CacheDatabase get() {
        return database;
    }
}
