package com.example.demoapplication.mvp.model;

import com.example.demoapplication.mvp.model.jsonmodel.LogoutBean;

public interface LogoutContract {
    interface ILogoutModel{
        interface OnFinishedListener{
            void onFinished(LogoutBean logoutBean);
            void onFailure();
        }

        void getLogoutData(OnFinishedListener onFinishedListener, String driverId);  //param after json parsed
    }
    interface LogoutView {
        void showProgress();
        void hideProgress();
        void setDataToView(LogoutBean logoutBean);
        void onResponseFailure();
    }

    interface ILogoutPresenter{
        void onDestroy();
        void requestDataFromServer(String driverId);
    }
}
