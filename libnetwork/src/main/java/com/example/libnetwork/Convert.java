package com.example.libnetwork;

import java.lang.reflect.Type;

/**
 * 接口数据转换成实体类
 *
 * @author iwen大大怪
 * Create to 2020/10/16 1:24
 */
public interface Convert<T> {
    T convert(String response, Type type);

    T convert(String response, Class claz);
}
