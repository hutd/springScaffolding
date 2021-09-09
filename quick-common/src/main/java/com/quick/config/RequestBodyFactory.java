package com.quick.config;

import com.alibaba.fastjson.JSONObject;
import com.quick.enums.ExceptionEnum;
import com.quick.exception.CamelliaException;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：chicunxiang
 * @date ：Created in 2020/9/14 10:15
 * @description：建造者模式的body生产工厂
 * @version: 1.0
 */
public class RequestBodyFactory {

    /**
     * 基础父类
     *
     * @param <T> 自己本身
     */
    private abstract static class BodyBuilder<T> {
        protected final Map<String, Object> params = new HashMap<String, Object>();
        protected final Map<String, String> header = new HashMap<String, String>();
        protected String url;

        public T add(String key, Object value) {
            params.put(key, value);
            return (T) this;
        }


        public T url(String url) {
            this.url = url;
            return (T) this;
        }

        public T addHeader(String name, String value) {
            header.put(name, value);
            return (T) this;
        }
        public abstract Request build();
    }

    /**
     * 产生json的body 用于post请求
     */
    public static class JsonBodyBuilder extends BodyBuilder<JsonBodyBuilder> {

        @Override
        public Request build() {
            RequestBody body;
            if (params.size() > 0) {
                body = FormBody.create(MediaType.parse("application/json"), JSONObject.toJSONString(params));
            } else {
                body = FormBody.create(null, "");
            }
            Request.Builder builder = new Request.Builder().url(this.url).post(body);
            if (header.size() > 0) {
                for (String key : header.keySet()) {
                    builder.addHeader(key, header.get(key));
                }
            }
            return builder.build();
        }
    }

    /**
     * 生成get请求. 参数自动拼接
     */
    public static class GetBodyBuilder extends BodyBuilder<GetBodyBuilder> {

        @Override
        public Request build() {
            if (StringUtils.isBlank(this.url)) {
                throw new CamelliaException(ExceptionEnum.SYSTEM_UNKNOWN_FAILED);
            }
            Request.Builder builder = new Request.Builder();
            HttpUrl httpUrl = HttpUrl.parse(this.url);
            if (httpUrl == null) {
                throw new CamelliaException(ExceptionEnum.SYSTEM_UNKNOWN_FAILED);
            }
            HttpUrl.Builder httpUrlBuilder = httpUrl.newBuilder();
            if (this.params.size() > 0) {
                for (String key : this.params.keySet()) {
                    httpUrlBuilder.addQueryParameter(key, String.valueOf(this.params.get(key)));
                }
            }
            builder.url(httpUrlBuilder.build());
            if (header.size() > 0) {
                for (String key : header.keySet()) {
                    builder.addHeader(key, header.get(key));
                }
            }
            return builder.build();
        }
    }

    /**
     * 生成form请求. 参数自动拼接
     */
    public static class FormBuilder extends BodyBuilder<FormBuilder> {


        private final Map<String, String> urlParams;

        public FormBuilder() {
            this.urlParams = new HashMap<>();
        }

        public FormBuilder addUrlParams(String key, String value) {
            urlParams.put(key, value);
            return this;
        }

        @Override
        public Request build() {
            if (StringUtils.isBlank(this.url)) {
                throw new CamelliaException(ExceptionEnum.SYSTEM_UNKNOWN_FAILED);
            }
            Request.Builder builder = new Request.Builder();

            FormBody.Builder formBuilder = new FormBody.Builder();

            if (this.params.size() > 0) {
                for (String key : this.params.keySet()) {
                    formBuilder.add(key, String.valueOf(this.params.get(key)));
                }
            }
            HttpUrl httpUrl = HttpUrl.parse(this.url);
            if (httpUrl == null) {
                throw new CamelliaException(ExceptionEnum.SYSTEM_UNKNOWN_FAILED);
            }
            HttpUrl.Builder httpUrlBuilder = httpUrl.newBuilder();
            if (this.urlParams.size() > 0) {
                for (String key : this.urlParams.keySet()) {
                    httpUrlBuilder.addQueryParameter(key, String.valueOf(this.urlParams.get(key)));
                }
            }
            builder.post(formBuilder.build()).url(httpUrlBuilder.build());
            
            if (header.size() > 0) {
                for (String key : header.keySet()) {
                    builder.addHeader(key, header.get(key));
                }
            }

            return builder.build();
        }
    }

    public static final class MultipartBuilder {

        private MultipartBody.Builder mBuilder;
        private String url;

        public MultipartBuilder() {
            mBuilder = new MultipartBody.Builder();
            mBuilder.setType(MultipartBody.FORM);
        }

        public MultipartBuilder add(String key, String value) {
            mBuilder.addFormDataPart(key, value);
            return this;
        }

        public MultipartBuilder addFile(String key, File file) {
            if (file.exists()) {
                mBuilder.addFormDataPart(key, file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file));
            }
            return this;
        }

        public MultipartBuilder url(String url){
            this.url = url;
            return this;
        }

        public MultipartBuilder addFile(String key,String fileName, byte[] bytes) {
            if (bytes.length > 0) {
                mBuilder.addFormDataPart(key, fileName,
                        RequestBody.create(MediaType.parse("multipart/form-data"), bytes));
            }
            return this;
        }

        public Request build() {

            if (StringUtils.isBlank(this.url)) {
                throw new CamelliaException(ExceptionEnum.SYSTEM_UNKNOWN_FAILED);
            }
            Request.Builder builder=new Request.Builder().url(this.url).post(mBuilder.build());
                    builder.addHeader("Content-type", "multipart/form-data");
            return builder.build();
        }
    }
}
