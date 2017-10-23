package com.liketry.ministore.adapter.mystore;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.liketry.ministore.common.BaseFragment;

import java.util.List;

/**
 * author Simon
 * create 2017/10/10
 * 我的商铺适配器
 */
public class StorePagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragments;

    public StorePagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }
}
