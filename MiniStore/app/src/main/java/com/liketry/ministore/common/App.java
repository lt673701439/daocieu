/**
 * @author Simon
 * @time 2017/5/31
 * App
 */
package com.liketry.ministore.common;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {
    private ExecutorService threadPool;//全局线程池
    private Map<String, Object> data;//全局数据存放
    private String userId = "9090101001";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    //添加任务
    public void addTask(BasicTask basicTask) {
        if (threadPool == null) {
            synchronized (App.class) {
                if (threadPool == null)
                    threadPool = Executors.newFixedThreadPool(5);
            }
        }
        if (basicTask != null)
            threadPool.submit(basicTask);
    }


    public Object getData(String key) {
        if (data == null)
            return null;
        return data.get(key);
    }

    public void setData(String key, Object obj) {
        if (data == null) {
            synchronized (App.class) {
                if (data == null) {
                    data = new HashMap<>();
                }
            }
        }
        data.put(key, obj);
    }

    public String getUserId() {
        return userId;
    }
}
