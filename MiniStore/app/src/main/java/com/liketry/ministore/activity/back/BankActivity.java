package com.liketry.ministore.activity.back;

import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.liketry.ministore.R;
import com.liketry.ministore.adapter.bank.BankAdapter;
import com.liketry.ministore.common.BaseActivity;

/**
 * author Simon
 * create 2017/10/10
 * 银行卡
 */
public class BankActivity extends BaseActivity {
    private RecyclerView recyclerView;

    @Override
    protected void handleMessage(int what, int arg1, int arg2, Object obj, Message msg) {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_bank);
        recyclerView = findViewById(R.id.rec_bank);
        recyclerView.setAdapter(new BankAdapter(this));
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
