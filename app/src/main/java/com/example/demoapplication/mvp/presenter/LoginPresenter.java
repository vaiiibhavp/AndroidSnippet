package com.example.demoapplication.mvp.presenter;

import com.example.demoapplication.RestAPI.ApiClient;
import com.example.demoapplication.RestAPI.ApiInterface;
import com.example.demoapplication.RestAPI.AppConstants;
import com.example.demoapplication.mvp.model.User;
import com.example.demoapplication.mvp.model.jsonmodel.LoginBean;
import com.example.demoapplication.mvp.view.ILoginView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements ILoginPresenter {
    ILoginView loginView;
    LoginBean loginBean;
    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void onLogin(String email, String password,String adsId) {

        User user=new User(email,password);

        int isLogin=user.isValidData();
        if(isLogin==0)
        {
            loginView.onLoginError("Please enter valid email");
        }else if(isLogin==2)
        {
            loginView.onLoginError("Please enter valid password");
        }else {

            ApiInterface service = ApiClient.getClient().create(ApiInterface.class);

     /*   String userFcmKey= FirebaseInstanceId.getInstance().getToken();
        if(userFcmKey!=null) {
            Log.i("FCM Key", userFcmKey);
        }else
        {
            userFcmKey="";
        }*/
            Observable<LoginBean> call=null;

            call = service.login(email, password, AppConstants.tokan,adsId);

            call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<LoginBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(LoginBean loginBeanObj) {
                           // progressDialog.dismiss();
                            loginBean = loginBeanObj;
                        }

                        @Override
                        public void onError(Throwable e) {

                           // loginView.onLoginError(e.getMessage());
                           // progressDialog.dismiss();
                            // showFailToast(ActivityLogin.this, getResources().getString(R.string.went_wrong));
                        }

                        @Override
                        public void onComplete() {

                            if (loginBean.getStatus() == 1) {
                                loginView.onLoginSucees(loginBean.getResult().get(0));
                            }else{
                                loginView.onLoginError(loginBean.getMsg());
                            }
                            // Updates UI with data
                          /*  progressDialog.dismiss();
                            if (loginBean.getStatus() == 1) {
                                LoginBean.Result result = loginBean.getResult().get(0);
                                if (result.getUserStatus().equalsIgnoreCase("0")) {
                                    showFailToast(ActivityLogin.this, "Your account has been disabled by admin");
                                } else {

                                    startActivity(new Intent(ActivityLogin.this, ActivityHomepage.class).putExtra("objNoti", (Bundle) null));
                                    finish();
                                }
                            } else {
                                showFailToast(ActivityLogin.this,  "" + loginBean.getMsg());
                            }*/
//                        cPresenter.updateCoinList(allCurrencyList);
                        }
                    });



        }
    }

}
