/**
 * @author Simon
 * @time 2017/5/31
 * activity基类
 */
package com.liketry.ministore.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.liketry.ministore.R;
import com.liketry.ministore.control.net.NetManager;
import com.liketry.ministore.control.net.RequestApi;
import com.liketry.ministore.model.common.BasicModel;
import com.liketry.ministore.utils.LogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected App app;
    protected Context context;
    protected SharedPreferences preferences;
    protected Handler handler;
    protected NetManager netManager;
    protected RequestApi request;
    private MaterialDialog progress = null;
    protected InputMethodManager inputMethodManager = null;

    protected abstract void handleMessage(int what, int arg1, int arg2, Object obj, Message msg);

    protected abstract void initView();

    protected abstract void logic();

    //网络请求成功时候返回
    protected abstract void networkResult(int what, Object obj);

    //网络请求失败时候返回
    protected abstract void networkError(int what, int error_code);

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        app = (App) getApplication();
        context = getApplicationContext();
        preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        handler = new BaseHandler() {
            @Override
            public void handleMessage(Message msg) {
                BaseActivity.this.handleMessage(msg.what, msg.arg1, msg.arg2, msg.obj, msg);
            }
        };
        netManager = NetManager.getInstance(context);
        request = netManager.getRequestApi();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        prepare();
        initView();
        logic();
    }

    protected void prepare() {
    }

    protected static class BaseHandler extends Handler {

    }

    protected <T> void requestNetwork(final int what, final Call<BasicModel<T>> call) {
        requestNetwork(what, true, call);
    }

    protected <T> void requestNetwork(final int what, boolean is_progress, final Call<BasicModel<T>> call) {
        if (is_progress) {
            showProgress();
        }
        call.enqueue(new Callback<BasicModel<T>>() {
            @Override
            public void onResponse(Call<BasicModel<T>> call, Response<BasicModel<T>> response) {
                cancleProgress();
                if (response == null) {
                    networkError(what, NetManager.NET_ERROR_SERVER);//服务器异常
                    return;
                }
                final BasicModel<T> basicModel = response.body();
                if (response.code() == NetManager.NET_SUCCESS_NUM) {
                    if (basicModel == null) {
                        networkError(what, NetManager.NET_ERROR_SERVER);//服务器错误
                    } else {
                        int code = basicModel.getCode();
                        if (code != 0) {
                            networkError(what, NetManager.NET_ERROR_DATA);
                        } else {
                            networkResult(what, basicModel.getResult());
                        }
                    }
                } else {
                    networkError(what, NetManager.NET_ERROR_SERVER);//服务器错误
                }
            }

            @Override
            public void onFailure(Call<BasicModel<T>> call, Throwable t) {
                cancleProgress();
                LogUtil.e("NET: what = ", what, " error = ", "onFailure" + t.toString());
                networkError(what, NetManager.NET_ERROR_CONNECTION);
            }
        });
    }

    //默认等待框
    protected void showProgress() {
        showProgress(R.string.net_loading);
    }

    //自定义文字等待框
    protected void showProgress(int string_id) {
        try {
            if (progress == null) {
                progress = new MaterialDialog.Builder(this)
                        .autoDismiss(false)
                        .cancelable(false)
                        .progress(true, 0)
                        .widgetColor(ContextCompat.getColor(context, R.color.white))
                        .progressIndeterminateStyle(false)
                        .content(string_id).show();
            } else {
                progress.show();
            }
        } catch (Exception e) {
            LogUtil.e("对话框，显示异常");
        }
    }

    //取消等待框
    protected void cancleProgress() {
        try {
            if (progress != null)
                progress.dismiss();
        } catch (Exception e) {
            LogUtil.e("对话框删除失败");
        }
    }

    //隐藏软键盘
    protected void hideSoftKeyboard() {
        if (inputMethodManager == null)
            inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusView = getCurrentFocus();
        if (focusView != null && focusView.getWindowToken() != null)
            inputMethodManager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
    }

    //是否有网络,如果没有返回false并且弹出消息提醒
    protected boolean haveNetworkAndToast() {
        if (NetManager.haveNetwork(context))
            return true;
        shortToast(R.string.app_no_network);
        return false;
    }

    //弹出短时间toast
    protected void shortToast(int resource) {
        try {
            Toast.makeText(context, getString(resource), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            LogUtil.v("展示异常");
        }
    }

    //弹出短时间toast
    protected void shortToast(String content) {
        if (!TextUtils.isEmpty(content))
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    //弹出短时间toast
    protected void longToast(int resource) {
        try {
            Toast.makeText(context, getString(resource), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            LogUtil.v("展示异常");
        }
    }

    //添加线程任务
    public void addTask(CommonTask task) {
        if (task == null) {
            LogUtil.vv("patient_measure", "任务为空，添加失败");
        } else {
            app.addTask(new BasicTask(context, handler, task));
            LogUtil.vv("patient_measure", "添加任务：", task.getClass().getSimpleName());
        }
    }

    public void createDialog(String content, MaterialDialog.SingleButtonCallback okClick) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this).title("提醒").titleGravity(GravityEnum.CENTER)
                .negativeText("取消").negativeColor(0xFFFFBDBD)
                .content(content).positiveText("确定");
        if (okClick != null) {
            builder.onPositive(okClick);
        }
        builder.show();
    }

    protected void setAppTitle(String title) {
        ((TextView) findViewById(R.id.txt_title_title)).setText(title);
    }

    protected void setAppTitle(String title, String rightBtn, boolean isBack, boolean isRight) {
        ((TextView) findViewById(R.id.txt_title_title)).setText(title);
        ((TextView) findViewById(R.id.txt_title_right)).setText(title);
        if (isBack) {
            findViewById(R.id.img_title_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        if (isRight) {
            findViewById(R.id.txt_title_right).setOnClickListener(this);
        }
    }
}