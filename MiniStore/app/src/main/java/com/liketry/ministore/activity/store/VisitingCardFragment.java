package com.liketry.ministore.activity.store;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.liketry.ministore.R;
import com.liketry.ministore.adapter.mystore.StoreTagAdapter;
import com.liketry.ministore.common.BaseFragment;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * author Simon
 * create 2017/10/10
 * 我的名片
 */
public class VisitingCardFragment extends BaseFragment {

    private TagFlowLayout tagLayout;

    @Override
    protected void handleMessage(int what, int arg1, int arg2, Object obj, Message msg) {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.fragment_visitingcard);
        tagLayout = findViewById(R.id.tag_visitingcard);
    }

    @Override
    protected void logic() {
        List<String> strs = new ArrayList<>();
        strs.add("1111");
        strs.add("22222");
        strs.add("3333333333");
        strs.add("444");
        strs.add("555555");
        tagLayout.setAdapter(new StoreTagAdapter(inflater, strs));
    }

    @Override
    protected void networkResult(int what, Object obj) {

    }

    @Override
    protected void networkError(int what, int error_code) {

    }
}
