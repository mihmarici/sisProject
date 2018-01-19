package com.mihovil.websecureaplication.WebService;

import com.mihovil.websecureaplication.Models.UserModel;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Mihovil on 10.1.2018..
 */

public interface APIinterface {

        @FormUrlEncoded
        @POST("loginOrRegistrate.php")
        Call<ServiceResponse> loginOrRegistrate(@Field("email") String email,@Field("password") String password);

        @FormUrlEncoded
        @POST("anyOtherAction.php")
        Call<ServiceResponse> anyOtherAction(@Header("token") String token/*,anyOtherAction */);
}
