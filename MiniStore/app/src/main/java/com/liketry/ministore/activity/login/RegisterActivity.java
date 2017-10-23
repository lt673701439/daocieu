package com.liketry.ministore.activity.login;

import android.os.Message;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liketry.ministore.R;
import com.liketry.ministore.common.BaseActivity;
import com.liketry.ministore.common.Constants;
import com.liketry.ministore.model.login.UserMobile;
import com.liketry.ministore.model.request.CodeBody;
import com.liketry.ministore.model.request.RegisterBody;
import com.liketry.ministore.utils.MobileUtil;


public class RegisterActivity extends BaseActivity implements TextWatcher {
    private final int NET_CODE = 1;//获取验证码
    private final int NET_REGISTER = 2;//注册
    private final int HANDLER_COUNT_DOWN = 1;
    private final int ERROR_USER_EXIST = 10020;
    private RelativeLayout topLayout;
    private RelativeLayout centerLayout;
    private LinearLayout codeLayout;
    private View centerLine;
    private EditText code1Edt;
    private EditText code4Edt;
    private EditText code3Edt;
    private EditText code2Edt;
    private EditText mobileEdt;
    private TextView remindTxt;
    private ImageView mobileImg;
    private boolean isAnim;
    private boolean isWaitCode;
    private String requestCodeMobile;//请求验证码的手机号

    @Override
    protected void handleMessage(int what, int arg1, int arg2, Object obj, Message msg) {
        switch (what) {
            case HANDLER_COUNT_DOWN:
                if (arg1 > 0)
                    remindTxt.setText("重新获取( " + arg1 + " )");
                else {
                    remindTxt.setText("重新获取");
                    isWaitCode = false;
                }
                break;
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_register);
        topLayout = findViewById(R.id.rl_register_toplayout);
        centerLayout = findViewById(R.id.rl_register_centerlayout);
        codeLayout = findViewById(R.id.rl_register_codelayout);
        mobileEdt = findViewById(R.id.edt_register_mobile);
        remindTxt = findViewById(R.id.txt_register_remind);
        mobileImg = findViewById(R.id.img_register_cancle);
        code1Edt = findViewById(R.id.edt_register_code1);
        code2Edt = findViewById(R.id.edt_register_code2);
        code3Edt = findViewById(R.id.edt_register_code3);
        code4Edt = findViewById(R.id.edt_register_code4);
        centerLine = findViewById(R.id.v_register_centerline);

        mobileEdt.addTextChangedListener(this);
        mobileEdt.setOnClickListener(this);
        mobileImg.setOnClickListener(this);
        remindTxt.setOnClickListener(this);
        code1Edt.addTextChangedListener(new MyTextWatcher(code1Edt));
        code2Edt.addTextChangedListener(new MyTextWatcher(code2Edt));
        code3Edt.addTextChangedListener(new MyTextWatcher(code3Edt));
        code4Edt.addTextChangedListener(new MyTextWatcher(code4Edt));
    }

    @Override
    protected void logic() {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String mobile = mobileEdt.getText().toString();
        mobileImg.setVisibility(mobile.length() > 0 ? View.VISIBLE : View.INVISIBLE);
        remindTxt.setTextColor(mobile.length() == 11 ? ContextCompat.getColor(context, R.color.code_orange) : ContextCompat.getColor(context, R.color.gray_d15b));
    }

    @Override
    protected void networkResult(int what, Object obj) {
        switch (what) {
            case NET_CODE:
                shortToast("验证码发送成功");
                mobileAnim();
                codeLayout.setVisibility(View.VISIBLE);
                centerLine.setVisibility(View.INVISIBLE);
                remindTxt.setText("");
                remindTxt.setTextColor(ContextCompat.getColor(context, R.color.gray_8e));
                changeFocus(code1Edt);

                new Thread() {
                    @Override
                    public void run() {
                        int i = 60;
                        while (i >= 0) {
                            Message msg = Message.obtain();
                            msg.what = HANDLER_COUNT_DOWN;
                            msg.arg1 = i;
                            handler.sendMessage(msg);
                            i--;
                            SystemClock.sleep(1000);
                        }
                    }
                }.start();
                break;
            case NET_REGISTER:
                UserMobile user = (UserMobile) obj;
                app.setData(Constants.M_KEY_USER, user);
                app.setData(Constants.M_KEY_UID, user.getId());
                break;
        }
    }

    @Override
    protected void networkError(int what, int error_code) {
        switch (what) {
            case NET_CODE:
                isWaitCode = false;
                shortToast("验证码发送失败");
                break;
            case NET_REGISTER:
                if (error_code == ERROR_USER_EXIST) {
                    shortToast("该用户已存在");
                } else {
                    shortToast("注册失败");
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_register_mobile:
                if (!isAnim) {
                    topAnim();
                }
                break;
            case R.id.img_register_cancle:
                mobileEdt.setText("");
                break;
            case R.id.txt_register_remind:
                getCode();
                break;
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    private void topAnim() {
        isAnim = true;
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, -MobileUtil.toPx(context, 35));
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                centerLayout.clearAnimation();
                int def = -MobileUtil.toPx(context, 35);
                centerLayout.setPadding(0, centerLayout.getPaddingTop() + def, 0, 0);
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        anim.setDuration(500);
        anim.setFillAfter(true);
        anim.setInterpolator(new AccelerateInterpolator());
        centerLayout.startAnimation(anim);
    }

    //发送验证码
    private void getCode() {
        if (isWaitCode)
            return;
        isWaitCode = true;
        requestCodeMobile = mobileEdt.getText().toString();
        if (!MobileUtil.isMobile(requestCodeMobile)) {
            isWaitCode = false;
            shortToast("请输入正确手机格式");
            return;
        }
        mobileEdt.setEnabled(false);
        requestNetwork(NET_CODE, true, request.getLoginCode(new CodeBody(requestCodeMobile, 0)));
    }

    private void mobileAnim() {
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, -MobileUtil.toPx(context, 40));
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                mobileEdt.clearAnimation();
                int def = -MobileUtil.toPx(context, 35);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mobileEdt.getLayoutParams();
                params.topMargin = params.topMargin + def;
                mobileEdt.setLayoutParams(params);
            }

            @Override
            public void onAnimationStart(Animation animation) {
                mobileEdt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                mobileEdt.clearFocus();
                mobileImg.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        anim.setDuration(500);
        anim.setFillAfter(true);
        anim.setInterpolator(new AccelerateInterpolator());
        mobileEdt.startAnimation(anim);
    }

    class MyTextWatcher implements TextWatcher {
        private EditText edt;

        public MyTextWatcher(EditText edt) {
            this.edt = edt;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (edt.getId()) {
                case R.id.edt_register_code1:
                    changeFocus(editable.toString().length() == 1 ? code2Edt : code1Edt);
                    break;
                case R.id.edt_register_code2:
                    changeFocus(editable.toString().length() == 1 ? code3Edt : code1Edt);
                    break;
                case R.id.edt_register_code3:
                    changeFocus(editable.toString().length() == 1 ? code4Edt : code2Edt);
                    break;
                case R.id.edt_register_code4:
                    if (editable.toString().length() == 1) {
                        String code1 = code1Edt.getText().toString();
                        String code2 = code2Edt.getText().toString();
                        String code3 = code3Edt.getText().toString();
                        String code4 = code4Edt.getText().toString();
                        if (code1.length() != 1 || code1.length() != 1 || code1.length() != 1 || code1.length() != 1) {
                            return;
                        }
                        String code = code1 + code2 + code3 + code4;
                        hideSoftKeyboard();
                        if (!mobileEdt.getText().toString().equals(requestCodeMobile)) {
                            shortToast("你请求验证码的手机号和当前注册号码不一致");
                            return;
                        }
                        requestNetwork(NET_REGISTER, request.register(new RegisterBody(requestCodeMobile, code, 0)));
                    } else {
                        changeFocus(code3Edt);
                    }
                    break;
            }
        }
    }

    private void changeFocus(EditText edt) {
        edt.setFocusable(true);
        edt.setFocusableInTouchMode(true);
        edt.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

}