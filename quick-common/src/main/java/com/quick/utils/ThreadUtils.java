package com.quick.utils;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author ：chicunxiang
 * @date ：Created in 2020/12/16 16:51
 * @description：线程池
 * @version: 1.0
 */
public class ThreadUtils {


    private static ScheduledThreadPoolExecutor fixedThreadPool = null;


    public static void init(){
        fixedThreadPool = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors()+1, new BasicThreadFactory.Builder().namingPattern("example-scheduled-pool-%d").daemon(true).build());
    }


    public static void execute(Runnable runnable) {
        fixedThreadPool.execute(runnable);
    }


    public static void cancel() {
        fixedThreadPool.shutdown();
        while (true) {
            //等待所有任务都执行结束
            if (fixedThreadPool.isTerminated()) {
                //所有的子线程都结束了
                fixedThreadPool = null;
                return;
            }
        }
    }


}
