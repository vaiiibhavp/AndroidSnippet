package com.example.demoapplication.mvp.model;

import com.example.demoapplication.mvp.model.jsonmodel.HomeBean;

public interface HomeContract {
    interface IHomeModel{
        interface OnFinishedListener{
            void onFinished(HomeBean homeBean);
            void onFailure();
        }

        void getHomeData(OnFinishedListener onFinishedListener,String vehicleId,String vehicleNo,String org_id);  //param after json parsed
    }
    interface HomeView {
        void showProgress();
        void hideProgress();
        void setDataToView(HomeBean homeBean);
        void onResponseFailure();
    }

    interface IHomePresenter{
        void onDestroy();
        void requestDataFromServer(String vehicleId,String vehicleNo,String org_id);
    }
}
