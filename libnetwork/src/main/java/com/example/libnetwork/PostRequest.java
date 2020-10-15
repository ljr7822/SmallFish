package com.example.libnetwork;

import java.util.Map;

import okhttp3.FormBody;

/**
 * post请求
 *
 * @author iwen大大怪
 * Create to 2020/10/16 1:09
 */
public class PostRequest<T> extends Request<T, PostRequest> {

    public PostRequest(String url) {
        super(url);
    }

    @Override
    protected okhttp3.Request generateRequest(okhttp3.Request.Builder builder) {
        // post请求表单提交
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : mParams.entrySet()) {
            bodyBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
        }
        okhttp3.Request request = builder.url(mUrl).post(bodyBuilder.build()).build();
        return request;
    }
}
