package com.quick.interceptors;


import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class HandlerInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder builder = request.newBuilder();
        builder.addHeader("User-Agent", "quick-service")
                // 请求返回添加这个header, 请求结果会被压缩. 所以注释掉, gzip
//                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "Keep-Alive");
        String header = request.header("Content-Type");
        if (header == null) {
            builder.addHeader("Content-Type", "application/json");
        }
        return chain.proceed(builder.build());
    }
}