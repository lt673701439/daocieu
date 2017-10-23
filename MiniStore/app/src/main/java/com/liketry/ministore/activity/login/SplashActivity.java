package com.liketry.ministore.activity.login;

import android.content.Intent;
import android.os.Message;
import android.view.View;

import com.liketry.ministore.activity.store.MyStoreActivity;
import com.liketry.ministore.common.BaseActivity;

/**
 * author Simon
 * create 2017/10/10
 * 启动页面
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void handleMessage(int what, int arg1, int arg2, Object obj, Message msg) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void logic() {
        startActivity(new Intent(this, RegisterActivity.class));
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
