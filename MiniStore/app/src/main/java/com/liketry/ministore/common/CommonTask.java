/**
 * Copyright(c) 2016 iHealth, All Rights Reserved.
 * Author: Simon
 * Created time on 2016/4/14
 */
package com.liketry.ministore.common;

import android.content.Context;
import android.os.Message;

/**
 * 全局基本任务类,运行于线程
 */
public abstract class CommonTask {
    private int what;

    public int getWhat() {
        return what;
    }

    public CommonTask() {

    }

    /**
     * @param what 状态码 ,如果非0 则返回到handler。0则代码只是后台运行
     */
    public CommonTask(int what) {
        this.what = what;
    }

    public abstract void run(Context context, Message msg) throws Exception;
}
