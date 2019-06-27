package com.example.day_00_lainxi;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by $lzj on 2019/2/21.
 */
public class MyThreadUtils {

    private static MyThreadUtils myThreadUtils;
    private final ThreadPoolExecutor executor;

    private MyThreadUtils(){
        executor = new ThreadPoolExecutor(5,//核心线程数量,核心池的大小
                20,//线程池最大线程数
                30,//表示线程没有任务执行时最多保持多久时间会终止
                TimeUnit.SECONDS,//时间单位
                new LinkedBlockingQueue<Runnable>(),//任务队列,用来存储等待执行的任务
                Executors.defaultThreadFactory(),//线程工厂,如何去创建线程的
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static MyThreadUtils getMyThreadUtils() {
        if (myThreadUtils == null){
            synchronized (MyThreadUtils.class){
                if (myThreadUtils == null){
                    myThreadUtils = new MyThreadUtils();
                }
            }
        }
        return myThreadUtils;
    }

    public void excecute(Runnable runnable){
        if (runnable != null) {
            executor.execute(runnable);
        }
    }

    public void remove(Runnable runnable){
        if (runnable != null) {
            executor.remove(runnable);
        }
    }

    public void shutdown(){
        if (executor!=null){
            executor.shutdown();
        }
    }
}
