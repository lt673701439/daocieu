/**
 * @author Simon
 * @time 2017/6/1
 * 网络实例
 */
package com.liketry.ministore.control.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liketry.ministore.common.Constants;
import com.liketry.ministore.utils.FormatUtil;
import com.liketry.ministore.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManager {
    public static final int NET_ERROR_SERVER = -1013;//网络请求服务器错误
    public static final int NET_ERROR_DATA = -1014;//网络请求数据异常
    public static final int NET_SUCCESS_NUM = 200;
    public static final int NET_ERROR_CONNECTION = -1011;//网络连接失败。进入onFailure里面
    private static NetManager net;
    private final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private RequestApi request;
    private static ConnectivityManager manager;

    private NetManager(final Context context) {
        GsonConverterFactory factory = GsonConverterFactory.create(GSON);
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Constants.DOMAIN).addConverterFactory(factory);
        OkHttpClient.Builder okhttpBuilder = new OkHttpClient().newBuilder();
        okhttpBuilder.interceptors().add(
                new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().build();
                        if (Constants.IS_DEBUG) {//开发模式打印日志
                            long start_time = System.nanoTime();
                            Response response = chain.proceed(request);
                            long end_time = System.nanoTime();
                            int code = response.code();
                            if (code == NetManager.NET_SUCCESS_NUM) {
                                final String responseString = new String(response.body().bytes());
                                LogUtil.v(request.method(), " = ", request.url(), "   code = ", response.code(), " time = ", FormatUtil.getOneBitDecimal((end_time - start_time) / 1e6d), "ms  ", responseString);
                                return response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseString)).build();
                            } else {
                                LogUtil.e(request.method(), " = ", request.url(), "   code = ", response.code());
                            }
                        }
                        return chain.proceed(request);
                    }
                }

        );
        builder.client(okhttpBuilder.build());
        Retrofit retrofit = builder.build();
        request = retrofit.create(RequestApi.class);
    }

    public static NetManager getInstance(Context context) {
        if (net == null) {
            synchronized (NetManager.class) {
                if (net == null) {
                    net = new NetManager(context);
                }
            }
        }
        return net;
    }

    public RequestApi getRequestApi() {
        return request;
    }

    //是否有网络
    public static boolean haveNetwork(Context context) {
        if (manager == null) {
            manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        if (manager == null) {
            return false;
        }
        NetworkInfo network_info = manager.getActiveNetworkInfo();
        return network_info != null && network_info.isAvailable();
    }
}
