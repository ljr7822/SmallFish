package com.example.libnetwork;

/**
 * 返回结果包装类
 *
 * @author iwen大大怪
 * Create to 2020/10/16 0:47
 */
public class ApiResponse<T> {
    public boolean success;
    public int status;
    public String message;
    public T body;
}
