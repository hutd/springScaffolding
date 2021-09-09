package com.quick.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.quick.enums.ExceptionEnum;
import com.quick.exception.CamelliaException;
import com.quick.exception.CodeException;
import com.quick.utils.Utils;
import com.quick.vo.ResponseEntity;
import okhttp3.Request;
import okhttp3.Response;

import java.lang.reflect.Type;
import java.util.logging.Logger;

/**
 * 网络请求的管理器
 *
 * @author chicunxiang
 */
public class HttpServiceManager {

    private final Logger logger = Logger.getLogger(HttpServiceManager.class.getName());

    private static HttpServiceManager sHttpServiceManager;

    private HttpServiceManager() {
    }

    public static HttpServiceManager getInstance() {
        if (sHttpServiceManager == null) {
            sHttpServiceManager = new HttpServiceManager();
        }
        return sHttpServiceManager;
    }

    /**
     * 同步方法
     *
     * @param request 请求的包
     * @param clazz   需要转换的类
     * @param <T>     泛型
     * @return 返回结构
     */
    public <T> ResponseEntity<T> execute(Request request, Class<T> clazz) {
        return (ResponseEntity<T>) execute(request, clazz, false, null);
    }

    public <T> ResponseEntity<T> execute(Request request, Type type) {
        return (ResponseEntity<T>) execute(request, null, false, type);
    }

    public <T> Object execute(Request request, Class<T> clazz, boolean redirectClazz) {
        return execute(request, clazz, redirectClazz, null);
    }

    /**
     * @param request       请求的包
     * @param clazz         需要转换的类
     * @param redirectClazz 重新指向clazz . 定义不同
     * @param <T>           泛型
     * @return
     */

    public <T> Object execute(Request request, Class<T> clazz, boolean redirectClazz, Type type) {
        Object res = null;
        try {
            Response response = OkHttpManager.getInstance().build().newCall(request).execute();

            String json = response.body().string();

            logger.info(json);
            // json转换
            // fastjson 似乎有点问题, 必须要加注解才能正常使用
            if (clazz != null) {
                res = JSON.parseObject(json, redirectClazz ? clazz : new TypeReference<ResponseEntity<T>>(clazz) {
                }.getType());
            } else {
                res = JSON.parseObject(json, type);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 如果res没有. 就直接报错
        if (res == null) {
            throw new CamelliaException(ExceptionEnum.SYSTEM_UNKNOWN_FAILED);
        }

        if (res instanceof ResponseEntity) {
            ResponseEntity<T> result = (ResponseEntity<T>) res;
            if (result.getCode() == null) {
                return res;
            }
        }

        return res;
    }

}
