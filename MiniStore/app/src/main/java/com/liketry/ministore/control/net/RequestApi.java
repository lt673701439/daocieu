/**
 * @author Simon
 * @time 2017/6/6
 * 接口
 */
package com.liketry.ministore.control.net;

import com.liketry.ministore.model.common.BasicModel;
import com.liketry.ministore.model.login.LoginModel;
import com.liketry.ministore.model.login.UserMobile;
import com.liketry.ministore.model.request.CodeBody;
import com.liketry.ministore.model.request.RegisterBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface RequestApi {

    //获取所有祝福类型和内容0-注册,1-添加银行卡，2-绑定微信
    @POST("api/user_api/getIdentifyingCode")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    Call<BasicModel<String>> getLoginCode(@Body CodeBody body);

    //获取所有祝福类型和内容
    @POST("api/user_api/register")
    Call<BasicModel<UserMobile>> register(@Body RegisterBody body);
}