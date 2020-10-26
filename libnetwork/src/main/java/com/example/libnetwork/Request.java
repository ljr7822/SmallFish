package com.example.libnetwork;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import androidx.annotation.IntDef;
import androidx.arch.core.executor.ArchTaskExecutor;

import com.example.libnetwork.cache.CacheManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 网络请求类
 *
 * @author iwen大大怪
 * Create to 2020/10/16 0:30
 */
public abstract class Request<T, R extends Request> implements Cloneable{
    protected String mUrl;
    protected HashMap<String, String> headers = new HashMap<>();
    protected HashMap<String, Object> mParams = new HashMap<>();

    // 仅仅只访问本地缓存，即便本地缓存不存在，也不会发起网络请求
    public static final int CACHE_ONLY = 1;
    // 先访问缓存，同时发起网络的请求，成功后缓存到本地
    public static final int CACHE_FIRST = 2;
    // 仅仅只访问服务器，本地不做任何存储
    public static final int NET_ONLY = 3;
    // 先访问网络，成功后缓存到本地
    public static final int NET_CACHE = 4;
    private String cacheKey;
    private Type mType;
    private Class mClaz;
    private int mCacheStrategy;

    @IntDef({CACHE_ONLY, CACHE_FIRST, NET_ONLY, NET_CACHE})
    public @interface CacheStrategy {

    }

    public Request(String mUrl) {
        this.mUrl = mUrl;
    }

    public R addHeader(String key, String value) {
        headers.put(key, value);
        return (R) this;
    }

    public R addParam(String key, Object value) {
        if (value == null) {
            return (R) this;
        }

        // value只能为int byte char short long double float boolean 和他们的包装类型，但是除了String.class 所以要额外判断
        if (value.getClass() == String.class) {
            mParams.put(key, value);
        } else {
            try {
                Field field = value.getClass().getField("TYPE");
                Class clazz = (Class) field.get(null);
                if (clazz.isPrimitive()) {
                    mParams.put(key, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (R) this;
    }

    public R cacheStrategy(@CacheStrategy int cacheStrategy) {
        mCacheStrategy = cacheStrategy;
        return (R) this;
    }

    public R cacheKey(String cacheKey) {
        cacheKey = cacheKey;
        return (R) this;
    }

    public R responseType(Type type) {
        mType = type;
        return (R) this;
    }

    public R responseType(Class claz) {
        mClaz = claz;
        return (R) this;
    }

    private Call getCall() {
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        addHeaders(builder);
        okhttp3.Request request = generateRequest(builder);
        Call call = ApiService.okHttpClient.newCall(request);
        return call;
    }

    // 开始网络请求
    @SuppressLint("RestrictedApi")
    public void execute(final JsonCallBack<T> callBack) {
        if (mCacheStrategy != NET_ONLY){
            ArchTaskExecutor.getIOThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                   ApiResponse<T> response = readCache();
                   if (callBack!= null){
                       callBack.onCacheSuccess(response);
                   }
                }
            });
        }
        if (mCacheStrategy!=CACHE_ONLY){
            // 异步
            getCall().enqueue(new Callback() {
                // 失败的回调
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    ApiResponse<T> response = new ApiResponse<>();
                    response.message = e.getMessage();
                    callBack.onError(response);
                }

                // 成功的回调
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    ApiResponse<T> apiResponse = parseResponse(response, callBack);
                    // 业务层面判断是否成功
                    if (apiResponse.success) {
                        callBack.onError(apiResponse);
                    } else {
                        callBack.onSuccess(apiResponse);
                    }
                }
            });
        }
    }

    private ApiResponse<T> readCache() {
        String key = TextUtils.isEmpty(cacheKey)?generateCacheKey():cacheKey;
        Object cache = CacheManager.getCache(key);
        ApiResponse<T> result = new ApiResponse<>();
        result.status = 304;
        result.message = "缓存获取成功";
        result.body = (T) cache;
        result.success = true;
        return result;
    }

    private ApiResponse<T> parseResponse(Response response, JsonCallBack<T> callBack) {
        String message = null;
        int status = response.code();
        boolean success = response.isSuccessful();
        ApiResponse<T> result = new ApiResponse<>();
        Convert concert = ApiService.sConcert;
        try {
            String content = response.body().string();
            if (success) {
                if (callBack != null) {
                    ParameterizedType type = (ParameterizedType) callBack.getClass().getGenericSuperclass();
                    Type argument = type.getActualTypeArguments()[0];
                    result.body = (T) concert.convert(content, argument);
                } else if (mType != null) {
                    result.body = (T) concert.convert(content, mType);
                } else if (mClaz != null) {
                    result.body = (T) concert.convert(content, mClaz);
                } else {
                    // TODO:打个日志
                }
            } else {
                message = content;
            }
        } catch (Exception e) {
            message = e.getMessage();
            success = false;
        }
        result.success = success;
        result.status = status;
        result.message = message;

        if (mCacheStrategy != NET_ONLY && result.success && result.body != null && result.body instanceof Serializable) {
            saveCache(result.body);
        }
        return result;
    }

    private void saveCache(T body) {
        String key = TextUtils.isEmpty(cacheKey) ? generateCacheKey() : cacheKey;
        CacheManager.save(key, body);
    }

    private String generateCacheKey() {
        cacheKey = UrlCreator.createUrlFromParams(mUrl, mParams);
        return cacheKey;
    }

    protected abstract okhttp3.Request generateRequest(okhttp3.Request.Builder builder);

    protected void addHeaders(okhttp3.Request.Builder builder) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
    }

    // 同步
    public ApiResponse<T> execute() {
        if (mCacheStrategy == CACHE_ONLY){
            return readCache();
        }
        try {
            Response response = getCall().execute();
            ApiResponse<T> result = parseResponse(response, null);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
