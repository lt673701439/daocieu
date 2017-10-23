package com.liketry.ministore.activity.store;

import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liketry.ministore.R;
import com.liketry.ministore.adapter.mystore.StorePagerAdapter;
import com.liketry.ministore.common.BaseActivity;
import com.liketry.ministore.common.BaseFragment;
import com.liketry.ministore.utils.MobileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author Simon
 * create 2017/10/10
 * 我的商铺
 */
public class MyStoreActivity extends BaseActivity {
    private ViewPager pager;
    private RelativeLayout indexView;//下游标
    private boolean isAnimRun;//是否执行动画
    private VisitingCardFragment vcFragment;
    private StuffFragment sFragment;
    private VerifyFragment vFragment;

    private TextView visitingCardTxt;
    private TextView stuffCardTxt;
    private TextView visitingTxt;


    @Override
    protected void handleMessage(int what, int arg1, int arg2, Object obj, Message msg) {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_mystore);
        pager = findViewById(R.id.pager_mystore);
        indexView = findViewById(R.id.rl_mystore_index);
        visitingCardTxt = findViewById(R.id.txt_mystore_visitingcard);
        stuffCardTxt = findViewById(R.id.txt_mystore_stuff);
        visitingTxt = findViewById(R.id.txt_mystore_verify);

        visitingCardTxt.setOnClickListener(this);
        stuffCardTxt.setOnClickListener(this);
        visitingTxt.setOnClickListener(this);
    }

    @Override
    protected void logic() {
        initIndex();
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(vcFragment = new VisitingCardFragment());
        fragments.add(sFragment = new StuffFragment());
        fragments.add(vFragment = new VerifyFragment());
        pager.setAdapter(new StorePagerAdapter(getSupportFragmentManager(), fragments));
    }

    @Override
    protected void networkResult(int what, Object obj) {

    }

    @Override
    protected void networkError(int what, int error_code) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_mystore_visitingcard:
                moveIndex(0);
                break;
            case R.id.txt_mystore_stuff:
                moveIndex(1);
                break;
            case R.id.txt_mystore_verify:
                moveIndex(2);
                break;
        }
    }

    private void initIndex() {
        ViewGroup.LayoutParams params = indexView.getLayoutParams();
        params.width = MobileUtil.getMobileWidth(context) / 3;
        indexView.setLayoutParams(params);
    }

    private void moveIndex(int position) {
        if (isAnimRun)
            return;
        isAnimRun = true;
        visitingCardTxt.setTextColor(ContextCompat.getColor(context, R.color.gray));
        stuffCardTxt.setTextColor(ContextCompat.getColor(context, R.color.gray));
        visitingTxt.setTextColor(ContextCompat.getColor(context, R.color.gray));
        final int endX;
        switch (position) {
            case 0:
                endX = 0;
                visitingCardTxt.setTextColor(ContextCompat.getColor(context, R.color.black));
                break;
            case 1:
                endX = MobileUtil.getMobileWidth(context) / 3;
                stuffCardTxt.setTextColor(ContextCompat.getColor(context, R.color.black));
                break;
            default:
                endX = MobileUtil.getMobileWidth(context) / 3 * 2;
                visitingTxt.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
        TranslateAnimation anim = new TranslateAnimation(0, endX - indexView.getX(), 0, 0);
        anim.setDuration(500);
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                indexView.setX(endX);
                indexView.clearAnimation();
                isAnimRun = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        indexView.startAnimation(anim);
    }
}