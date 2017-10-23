package com.liketry.ministore.adapter.bank;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.liketry.ministore.R;
import com.liketry.ministore.common.BaseActivity;

/**
 * author Simon
 * create 2017/10/12
 * 银行卡适配器
 */
public class BankAdapter extends RecyclerView.Adapter<BankAdapter.MyHolder> {
    private LayoutInflater inflater;

    public BankAdapter(Activity activity) {
        inflater = activity.getLayoutInflater();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.item_bankcard, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }
    }
}