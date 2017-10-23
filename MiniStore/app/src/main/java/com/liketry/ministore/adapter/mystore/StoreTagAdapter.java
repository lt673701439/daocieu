package com.liketry.ministore.adapter.mystore;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.liketry.ministore.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * author Simon
 * create 2017/10/10
 * 我的名片标签
 */
public class StoreTagAdapter extends TagAdapter<String> {
    private LayoutInflater inflater;

    public StoreTagAdapter(LayoutInflater inflater, List<String> datas) {
        super(datas);
        this.inflater = inflater;
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        TextView tv = (TextView) inflater.inflate(R.layout.item_tag_content, null, false);
        tv.setText(s);
        return tv;
    }
}
