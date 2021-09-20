package com.example.demoapplication.service;

import com.example.demoapplication.RestAPI.ApiClient;
import com.example.demoapplication.RestAPI.ApiInterface;
import com.example.demoapplication.mvp.model.LogoutContract;
import com.example.demoapplication.mvp.model.jsonmodel.LogoutBean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LogoutModel implements LogoutContract.ILogoutModel {


    @Override
    public void getLogoutData(final OnFinishedListener onFinishedListener,String driverId) {
        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

        Observable<LogoutBean> call = service.getLogout(driverId);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LogoutBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LogoutBean logoutBean) {

                        if(logoutBean.getStatus() == 1) {
                            onFinishedListener.onFinished(logoutBean);
                        }else
                        {
                            onFinishedListener.onFailure();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        onFinishedListener.onFailure();
                    }

                    @Override
                    public void onComplete() {


                    }
                });
    }
}
