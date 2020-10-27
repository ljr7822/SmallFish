package com.example.libnetwork;

/**
 * 默认的jsonCallBack, 子类可选择性复写某一回调方法
 * author iwen大大怪
 * Create to 2020/10/16 0:46
 */
public abstract class JsonCallBack<T> {
    public void onSuccess(ApiResponse<T> response){

    }

    public void onError(ApiResponse<T> response){

    }

    public void onCacheSuccess(ApiResponse<T> response){

    }
}
