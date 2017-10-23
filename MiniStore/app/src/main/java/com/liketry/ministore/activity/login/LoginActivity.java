package com.liketry.ministore.activity.login;

import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.liketry.ministore.R;
import com.liketry.ministore.common.BaseActivity;

/**
 * author Simon
 * create 2017/10/9
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements TextWatcher {
    private EditText mobileEdt;
    private EditText pwdEdt;
    private ImageView mobileImg;
    private ImageView pwdImg;
    private RelativeLayout btnLayout;
    private boolean isShowPwd;

    @Override
    protected void handleMessage(int what, int arg1, int arg2, Object obj, Message msg) {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        mobileEdt = findViewById(R.id.edt_login_mobile);
        pwdEdt = findViewById(R.id.edt_login_pwd);
        mobileImg = findViewById(R.id.img_login_cancle);
        pwdImg = findViewById(R.id.img_login_see);
        btnLayout = findViewById(R.id.rl_login);

        mobileEdt.addTextChangedListener(this);
        pwdEdt.addTextChangedListener(this);
        pwdImg.setOnClickListener(this);
        btnLayout.setOnClickListener(this);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String mobile = mobileEdt.getText().toString();
        String pwd = pwdEdt.getText().toString();
        if (mobile.length() > 0) {
            mobileImg.setVisibility(View.VISIBLE);
        } else {
            mobileImg.setVisibility(View.INVISIBLE);
        }
        if (pwd.length() > 0) {
            pwdImg.setVisibility(View.VISIBLE);
            btnLayout.setVisibility(View.VISIBLE);
        } else {
            pwdImg.setVisibility(View.INVISIBLE);
            btnLayout.setVisibility(View.INVISIBLE);
        }
        if (pwd.length() < 6) {
            btnLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_d15b));
        } else {
            btnLayout.setBackgroundResource(R.mipmap.login_btn);
        }
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
        switch (view.getId()) {
            case R.id.img_login_see:
                isShowPwd = !isShowPwd;
                shortToast(isShowPwd + "");
                pwdImg.setImageResource(isShowPwd ? R.mipmap.login_see_open : R.mipmap.login_see_close);
                pwdEdt.setInputType(isShowPwd ? InputType.TYPE_CLASS_TEXT : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
}
