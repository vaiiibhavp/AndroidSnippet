package com.prometteur.dmsdriver.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.prometteur.dmsdriver.MainActivity;
import com.prometteur.dmsdriver.RestAPI.AppConstants;
import com.prometteur.dmsdriver.databinding.ActivityLoginBinding;
import com.prometteur.dmsdriver.mvp.model.jsonmodel.LoginBean;
import com.prometteur.dmsdriver.mvp.presenter.ILoginPresenter;
import com.prometteur.dmsdriver.mvp.presenter.LoginPresenter;
import com.prometteur.dmsdriver.mvp.view.ILoginView;
import com.prometteur.dmsdriver.utils.Preferences;

import static com.prometteur.dmsdriver.RestAPI.AppConstants.ISLOGIN;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.MOBNO;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.ORGID;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.USEREMAILKEY;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.USERFNAMEKEY;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.USERID;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.USERIDVAL;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.USERPROFILEKEY;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.USERSESSION;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.VEHICLEID;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.VEHICLENO;

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
        if (Preferences.getPreferenceValueInt(this, ISLOGIN) == 1) {
            startActivity(new Intent(activity, MainActivity.class));
            finish();
        }
        loginBinding.btnLogin.setOnClickListener(view -> {
            progressDialog.show();
            loginPresenter.onLogin(loginBinding.edtEmailId.getText().toString(), loginBinding.edtPassword.getText().toString(), adsId);
        });
        loginBinding.txtForgotPass.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, ForgotDialogActivity.class)));
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
        USERIDVAL = result.getDrivId();
        Preferences.setPreferenceValue(this, USERID, result.getDrivId());
        Preferences.setPreferenceValue(this, MOBNO, result.getDrivMobile());
        Preferences.setPreferenceValue(this, ISLOGIN, 1);
        Preferences.setPreferenceValue(this, USERSESSION, result.getDrivSession());
        Preferences.setPreferenceValue(this, USERPROFILEKEY, result.getDrivPhoto());
        Preferences.setPreferenceValue(this, USERFNAMEKEY, result.getDrivName());
        Preferences.setPreferenceValue(this, USEREMAILKEY, result.getDrivEmail());
        Preferences.setPreferenceValue(this, VEHICLENO, result.getVehiNo());
        Preferences.setPreferenceValue(this, VEHICLEID, result.getVehiId());
        Preferences.setPreferenceValue(this, ORGID, result.getDrivOrgId());
        startActivity(new Intent(activity, MainActivity.class));
        finish();
    }

    @Override
    public void onLoginSucees(String message) {
    }

    public void getAAID() {
        AsyncTask.execute(() -> {
            AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(LoginActivity.this);
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
