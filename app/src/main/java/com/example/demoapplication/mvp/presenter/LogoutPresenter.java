package com.example.demoapplication.mvp.presenter;

import com.example.demoapplication.mvp.model.LogoutContract;
import com.example.demoapplication.mvp.model.jsonmodel.LogoutBean;
import com.example.demoapplication.service.LogoutModel;

public class LogoutPresenter implements LogoutContract.ILogoutPresenter, LogoutContract.ILogoutModel.OnFinishedListener {
    private LogoutContract.LogoutView myRideView;
    private LogoutContract.ILogoutModel myRideModel;

    public LogoutPresenter(LogoutContract.LogoutView myRideView) {
        this.myRideView = myRideView;
        myRideModel =new LogoutModel();
    }

    @Override
    public void onDestroy() {
        this.myRideView = null;
    }

    @Override
    public void requestDataFromServer(String driverId) {
        if (myRideView != null) {
            myRideView.showProgress();
        }
        myRideModel.getLogoutData(this,driverId);
    }

    @Override
    public void onFinished(LogoutBean homeBean) {
        if (myRideView != null) {
            myRideView.hideProgress();
        }
        myRideView.setDataToView(homeBean);
    }

    @Override
    public void onFailure() {
        if (myRideView != null) {
            myRideView.hideProgress();
        }
        myRideView.onResponseFailure();

    }
}
