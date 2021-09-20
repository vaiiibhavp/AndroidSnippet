package com.example.demoapplication.mvp.view;

import com.example.demoapplication.mvp.model.jsonmodel.LoginBean;

public interface ILoginView {
    void onLoginError(String message);
    void onLoginSucees(LoginBean.Result result);
    void onLoginSucees(String message);
}
