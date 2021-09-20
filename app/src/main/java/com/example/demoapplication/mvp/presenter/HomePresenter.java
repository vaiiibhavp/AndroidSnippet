package com.example.demoapplication.mvp.presenter;

import com.example.demoapplication.mvp.model.jsonmodel.HomeBean;
import com.example.demoapplication.mvp.model.HomeContract;
import com.example.demoapplication.service.HomeModel;

public class HomePresenter implements HomeContract.IHomePresenter, HomeContract.IHomeModel.OnFinishedListener {
    private HomeContract.HomeView homeView;
    private HomeContract.IHomeModel homeModel;

    public HomePresenter(HomeContract.HomeView homeView) {
        this.homeView = homeView;
        homeModel =new HomeModel();
    }

    @Override
    public void onDestroy() {
        this.homeView = null;
    }

    @Override
    public void requestDataFromServer(String vehicleId,String vehicleNo,String org_id) {
        if (homeView != null) {
            homeView.showProgress();
        }
        homeModel.getHomeData(this,vehicleId,vehicleNo,org_id);
    }

    @Override
    public void onFinished(HomeBean homeBean) {
        homeView.setDataToView(homeBean);
        if (homeView != null) {
            homeView.hideProgress();
        }
    }

    @Override
    public void onFailure() {
        if (homeView != null) {
            homeView.hideProgress();
        }
        assert homeView != null;
        homeView.onResponseFailure();

    }
}
