package com.example.libnetwork;

/**
 * @author iwen大大怪
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
