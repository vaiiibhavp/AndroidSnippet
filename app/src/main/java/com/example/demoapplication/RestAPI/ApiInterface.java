package com.example.demoapplication.RestAPI;

import com.example.demoapplication.mvp.model.jsonmodel.HomeBean;
import com.example.demoapplication.mvp.model.jsonmodel.LoginBean;
import com.example.demoapplication.mvp.model.jsonmodel.LogoutBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Dhananjay on 27/05/21.
 */


public interface ApiInterface {
    @FormUrlEncoded
    @POST("mLogin")
    Observable<LoginBean> login(
            @Field("driv_email") String driv_email,
            @Field("driv_password") String driv_password,
            @Field("vehi_fcm_key") String vehi_fcm_key,
            @Field("vehi_iemi_no") String vehi_iemi_no
    );

    @FormUrlEncoded
    @POST("mLogout")
    Observable<LogoutBean> getLogout(
            @Field("driv_id") String driv_id
    );


    @FormUrlEncoded
    @POST("fields/home")
    public Observable<HomeBean> getHomeDetails(
            @Field("book_vehi_id") String book_vehi_id,
            @Field("book_vehi_no") String book_vehi_no,
            @Field("org_id") String org_id
    );
}