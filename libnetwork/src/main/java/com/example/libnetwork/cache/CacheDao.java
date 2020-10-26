package com.example.libnetwork.cache;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * author : Iwen大大怪
 * create : 2020/10/26 22:51
 */
@Dao
public interface CacheDao {

    // 数据库插入
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long save(Cache cache);

    // 数据库查询
    @Query("select * from cache where 'key'=:key")
    Cache getCache(String key);

    // 数据库删除
    @Delete
    int delete(String key);

    // 数据库更新
    @Update(onConflict = OnConflictStrategy.REPLACE)
    int update(Cache cache);
}
