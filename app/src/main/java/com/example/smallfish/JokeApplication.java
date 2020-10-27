package com.example.smallfish;

import android.app.Application;

import com.example.libnetwork.ApiService;

/**
 * 初始化网络请求
 * author : Iwen大大怪
 * create : 2020/10/27 19:12
 */
public class JokeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        123.56.232.18 172.17.123.33
        ApiService.init("http://123.56.232.18:8080/serverdemo", null);
    }
}
