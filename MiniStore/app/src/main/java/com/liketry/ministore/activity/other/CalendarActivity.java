package com.liketry.ministore.activity.other;

import android.os.Message;
import android.view.View;

import com.liketry.ministore.R;
import com.liketry.ministore.common.BaseActivity;

/**
 * author Simon
 * create 2017/10/12
 * 日历页面
 */
public class CalendarActivity extends BaseActivity {

    @Override
    protected void handleMessage(int what, int arg1, int arg2, Object obj, Message msg) {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_calendar);
        setAppTitle("选择时间", "完成", true, true);
    }

    @Override
    protected void logic() {

    }

    @Override
    protected void networkResult(int what, Object obj) {

    }

    @Override
    protected void networkError(int what, int error_code) {

    }

    @Override
    public void onClick(View view) {

    }
}