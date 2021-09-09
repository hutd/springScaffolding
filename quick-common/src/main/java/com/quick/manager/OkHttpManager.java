package com.quick.manager;

import com.quick.interceptors.HandlerInterceptor;
import com.quick.interceptors.LoggerInterceptor;
import lombok.SneakyThrows;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.util.concurrent.TimeUnit;

/**
 * okhttp统一配置
 * @author chicunxiang
 */
public class OkHttpManager {

    private static OkHttpManager sHttpManager;
    private        long          mTimeOut = 60;
    private        OkHttpClient  mOkHttpClient;

    public static OkHttpManager getInstance() {
        if (sHttpManager == null) {
            sHttpManager = new OkHttpManager();
        }
        return sHttpManager;
    }

    public void setTimeOut(long timeOut) {
        mTimeOut = timeOut;
    }

    @SneakyThrows
    public OkHttpClient build() {
        if (mOkHttpClient == null) {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HandlerInterceptor())
                    .addInterceptor(new LoggerInterceptor())
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier((s, sslSession) -> true)
                    .connectTimeout(mTimeOut, TimeUnit.SECONDS)
                    .readTimeout(mTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(mTimeOut, TimeUnit.SECONDS)
                    .build();
        }
        return mOkHttpClient;
    }

    public void addInterceptor(Interceptor interceptor) {
        mOkHttpClient = build().newBuilder().addInterceptor(interceptor).build();
    }
}
