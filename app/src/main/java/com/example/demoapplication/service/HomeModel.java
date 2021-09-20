package com.example.demoapplication.service;

import com.example.demoapplication.RestAPI.ApiClient;
import com.example.demoapplication.RestAPI.ApiInterface;
import com.example.demoapplication.mvp.model.jsonmodel.HomeBean;
import com.example.demoapplication.mvp.model.HomeContract;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class HomeModel implements HomeContract.IHomeModel {
    public HomeModel() {

    }

    @Override
    public void getHomeData(final OnFinishedListener onFinishedListener, String vehicleId,String vehicleNo, String org_id) {

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

        Observable<HomeBean> call = service.getHomeDetails(vehicleId,vehicleNo, org_id);

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeBean homeBean) {

                        if (homeBean.getStatus() == 1) {
                            onFinishedListener.onFinished(homeBean);
                        } else {
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
