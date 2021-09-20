package com.example.demoapplication.ui.activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapplication.RestAPI.AppConstants;
import com.example.demoapplication.databinding.ActivityLoginBinding;
import com.example.demoapplication.mvp.model.jsonmodel.LoginBean;
import com.example.demoapplication.mvp.presenter.ILoginPresenter;
import com.example.demoapplication.mvp.presenter.LoginPresenter;
import com.example.demoapplication.mvp.view.ILoginView;
import com.example.demoapplication.utils.Preferences;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    ActivityLoginBinding loginBinding;
    AppCompatActivity activity;
    ILoginPresenter loginPresenter;
    ProgressDialog progressDialog;
    String adsId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        FirebaseApp.initializeApp(this);
        getToken();
        getAAID();
        loginPresenter = new LoginPresenter(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        if (Preferences.getPreferenceValueInt(this, AppConstants.ISLOGIN) == 1) {
            startActivity(new Intent(activity, MainActivity.class));
            finish();
        }
        loginBinding.btnLogin.setOnClickListener(view -> {
            progressDialog.show();
            loginPresenter.onLogin(loginBinding.edtEmailId.getText().toString(), loginBinding.edtPassword.getText().toString(), adsId);
        });
//        loginBinding.txtForgotPass.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, ForgotDialogActivity.class)));
    }

    @Override
    public void onLoginError(String message) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSucees(LoginBean.Result result) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        AppConstants.USERIDVAL = result.getDrivId();
        Preferences.setPreferenceValue(this, AppConstants.USERID, result.getDrivId());
        Preferences.setPreferenceValue(this, AppConstants.MOBNO, result.getDrivMobile());
        Preferences.setPreferenceValue(this, AppConstants.ISLOGIN, 1);
        Preferences.setPreferenceValue(this, AppConstants.USERSESSION, result.getDrivSession());
        Preferences.setPreferenceValue(this, AppConstants.USERPROFILEKEY, result.getDrivPhoto());
        Preferences.setPreferenceValue(this, AppConstants.USERFNAMEKEY, result.getDrivName());
        Preferences.setPreferenceValue(this, AppConstants.USEREMAILKEY, result.getDrivEmail());
        Preferences.setPreferenceValue(this, AppConstants.VEHICLENO, result.getVehiNo());
        Preferences.setPreferenceValue(this, AppConstants.VEHICLEID, result.getVehiId());
        Preferences.setPreferenceValue(this, AppConstants.ORGID, result.getDrivOrgId());
        startActivity(new Intent(activity, MainActivity.class));
        finish();
    }

    @Override
    public void onLoginSucees(String message) {
    }

    public void getAAID() {
        AsyncTask.execute(() -> {
            AdvertisingIdClient.Info adInfo = null;
            try {
                adInfo = AdvertisingIdClient.getAdvertisingIdInfo(LoginActivity.this);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            }
            adsId = adInfo != null ? adInfo.getId() : null;
            if (adsId != null)
                Log.e("UIDMY", adsId);
        });
    }

    void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    AppConstants.tokan = token;
                });
    }

}
