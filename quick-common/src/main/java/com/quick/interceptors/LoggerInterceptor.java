package com.quick.interceptors;


import com.alibaba.fastjson.JSON;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * 请求日志
 * @author chicunxiang
 */
public class LoggerInterceptor implements Interceptor {
    private final String TAG = "request";

    private final Logger logger = Logger.getLogger(TAG);

    @Override
    public Response intercept(Chain chain) throws IOException {
        //Chain 里包含了request和response
        Request request   = chain.request();
        long    startTime = System.currentTimeMillis();
        i("----------开始请求 ");
        i("----------请求地址 : " + request.url() + " ");
        i("----------请求头 : " + request.headers() + " ");
        i("----------请求方式 : " + request.method() + " ");
        i("----------请求的body : " + JSON.toJSONString(request.body()) + " ");
        Response response = chain.proceed(request);
        long     endTime  = System.currentTimeMillis();
        long     duration = endTime - startTime;
        i("----------结束请求 : " + duration + " 毫秒");
        return response;
    }

    private void i( String msg) {
        logger.info(msg);
    }

}
