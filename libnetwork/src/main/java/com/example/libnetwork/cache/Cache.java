package com.example.libnetwork.cache;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * 缓存网络数据的数据库表
 * author : Iwen大大怪
 * create : 2020/10/26 22:40
 */
@Entity(tableName = "cache")
public class Cache implements Serializable {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String key;

    public byte[] data;
}
