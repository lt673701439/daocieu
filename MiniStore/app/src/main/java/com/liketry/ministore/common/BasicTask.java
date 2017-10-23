/**
 * Copyright(c) 2016 iHealth, All Rights Reserved.
 * Author: Simon
 * Created time on 2016/4/14
 */
package com.liketry.ministore.common;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.liketry.ministore.utils.LogUtil;


/**
 * 全局线程任务
 */
public class BasicTask implements Runnable {
    private Context context = null;
    private Handler handler = null;
    private int what = 0;
    private CommonTask task = null;

    public BasicTask(Context context, Handler handler, CommonTask task) {
        this.what = task.getWhat();
        this.context = context;
        this.handler = handler;
        this.task = task;
    }

    @Override
    public void run() {
        try {
            if (task == null) {
                if (what != 0)
                    handler.sendEmptyMessage(what);
                return;
            }
            LogUtil.vv("patient_measure", "执行任务： ", task.getClass().getSimpleName());
            Message msg = new Message();
            task.run(context, msg);
            msg.what = what;
            if (what != 0) {
                handler.sendMessage(msg);
            }
        } catch (Exception e) {
            if (task != null)
                LogUtil.e("全局线程任务异常   :", task.getClass().getName(), "  ", e.getMessage());
            else
                LogUtil.e("全局线程任务异常   : task == null");
            if (Constants.IS_DEBUG) {
                e.printStackTrace();
            }
            if (what != 0) {
                handler.sendEmptyMessage(what);
            }
        }
    }
}