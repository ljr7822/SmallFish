package com.example.libnetwork.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * author : Iwen大大怪
 * create : 2020/10/26 23:23
 */
public class CacheManager {
    // 写入数据库缓存
    public static <T> void save(String key, T body) {
        Cache cache = new Cache();
        cache.key = key;
        cache.data = toByteArray(body);

        CacheDatabase.get().getCacheDao().save(cache);
    }

    // 读取数据库缓存
    public static Object getCache(String key){
        Cache cache = CacheDatabase.get().getCacheDao().getCache(key);
        if (cache != null&&cache.data!=null){
            return toObject(cache.data);
        }
        return null;
    }

    /**
     * 将二进制数组转化成object
     * @param data
     * @return
     */
    private static Object toObject(byte[] data) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(data);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bais !=null){
                    bais.close();
                }
                if (ois!=null){
                    ois.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将object转化成二进制数组
     * @param body
     * @param <T>
     * @return
     */
    private static <T> byte[] toByteArray(T body) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(body);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos !=null){
                    baos.close();
                }
                if (oos!=null){
                    oos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }
}
