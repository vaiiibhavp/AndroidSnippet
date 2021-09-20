package com.example.demoapplication.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.demoapplication.R;
import com.example.demoapplication.databinding.FragmentHomeMapBinding;
import com.example.demoapplication.interfaces.DistanceTimeChangedListener;
import com.example.demoapplication.mvp.model.HomeContract;
import com.example.demoapplication.mvp.model.jsonmodel.HomeBean;
import com.example.demoapplication.mvp.presenter.HomePresenter;
import com.example.demoapplication.utils.Preferences;
import com.example.demoapplication.utils.ProjectUtils;

import java.util.List;

import static com.example.demoapplication.ui.activity.MainActivity.activity;
import static com.example.demoapplication.RestAPI.AppConstants.GT_MY_CURRENT_LOCATION_LATITUDE;
import static com.example.demoapplication.RestAPI.AppConstants.GT_MY_CURRENT_LOCATION_LONGITUDE;
import static com.example.demoapplication.RestAPI.AppConstants.ORGID;
import static com.example.demoapplication.RestAPI.AppConstants.USERPROFILEKEY;
import static com.example.demoapplication.RestAPI.AppConstants.USER_STATUS;
import static com.example.demoapplication.RestAPI.AppConstants.VEHICLEID;
import static com.example.demoapplication.RestAPI.AppConstants.VEHICLENO;

public class HomeMapFragment extends Fragment implements OnMapReadyCallback, HomeContract.HomeView, DistanceTimeChangedListener, GoogleMap.OnMarkerClickListener {
    Context context;
    FragmentHomeMapBinding homeMapBinding;
    HomePresenter homePresenter;
    ProgressDialog progressDialog;
    private GoogleMap mMap;
    LocationManager locationManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        homeMapBinding = FragmentHomeMapBinding.inflate(inflater, container, false);
        View view = homeMapBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        homePresenter = new HomePresenter(this);

        Glide.with(context).load(Preferences.getPreferenceValue(context, USERPROFILEKEY))
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(homeMapBinding.ivProfileImg);

        homeMapBinding.ivProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        checkPermissionRequests();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        homeMapBinding.rlMyCurrentStatus.setVisibility(View.VISIBLE);
        homeMapBinding.relTotalRides.setVisibility(View.VISIBLE);
        ProjectUtils.checkLanguage(context);

    }

    private static boolean Loc_permissiongranted = false;
    private static final int loc_request_code = 1234;

    private void checkPermissionRequests() {
        if (ContextCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                if (!Preferences.getPreferenceValueBoolean(activity, "autostart_show")) {
                    Preferences.setPreferenceValue(activity, "autostart_show", true);
                    new AlertDialog.Builder(activity)
                            .setTitle("DMS Autostart")
                            .setMessage("You need to enable the autostart permission. If you ignore this time when you have to do it manually.")
                            .setPositiveButton("Open", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String manufacturer = Build.MANUFACTURER;
                                    try {
                                        Intent intent = new Intent();
                                        if ("xiaomi".equalsIgnoreCase(manufacturer)) {
                                            intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                                        } else if ("asus".equalsIgnoreCase(manufacturer)) {
                                            intent.setComponent(new ComponentName("com.asus.mobilemanager", "com.asus.mobilemanager.powersaver.PowerSaverSettings"));
                                        } else if ("nokia".equalsIgnoreCase(manufacturer)) {
                                            intent.setComponent(new ComponentName("com.evenwell.powersaving.g3", "com.evenwell.powersaving.g3.exception.PowerSaverExceptionActivity"));
                                        } else if ("oppo".equalsIgnoreCase(manufacturer)) {
                                            intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
                                        } else if ("vivo".equalsIgnoreCase(manufacturer)) {
                                            intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
                                        } else if ("Letv".equalsIgnoreCase(manufacturer)) {
                                            intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
                                        } else if ("Honor".equalsIgnoreCase(manufacturer)) {
                                            intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
                                        } else if ("huawei".equalsIgnoreCase(manufacturer)) {
                                            intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"));
                                        }

                                        List<ResolveInfo> list = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                                        if (list.size() > 0) {
                                            startActivity(intent);
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            })
                            .create()
                            .show();
                }
            }

        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            Log.i("", "");
            new AlertDialog.Builder(activity)
                    .setTitle("Location permission")
                    .setMessage("you need to allow location permission")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(activity, ProjectUtils.getPermissionArray(),
                                    loc_request_code);
                        }
                    })
                    .create()
                    .show();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Log.i("", "");
            new AlertDialog.Builder(activity)
                    .setTitle("Location permission")
                    .setMessage("you need to allow location permission")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(activity, ProjectUtils.getPermissionArray(),
                                    loc_request_code);
                        }
                    })
                    .create()
                    .show();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                Log.i("", "");

                ActivityCompat.requestPermissions((Activity) activity,
                        ProjectUtils.getPermissionArray(), loc_request_code);
            } else {
                ActivityCompat.requestPermissions((Activity) activity,
                        ProjectUtils.getPermissionArray(), loc_request_code);
            }
        } else {
            ActivityCompat.requestPermissions((Activity) activity,
                    ProjectUtils.getPermissionArray(), loc_request_code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (loc_request_code == requestCode) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted
                Loc_permissiongranted = true;
                boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (isNetworkEnabled) {
                    //check the network permission
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        /*&& ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {
                        checkPermissionRequests();
                    } else {

                    }
                }
            } else {
                //show some warning
            }

            // enableLocation(nActivity);
            //getDeviceLocation();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        homePresenter.requestDataFromServer(Preferences.getPreferenceValue(context, VEHICLEID), Preferences.getPreferenceValue(context, VEHICLENO), Preferences.getPreferenceValue(context, ORGID));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng TutorialsPoint = new LatLng(16.8, 74.8);
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style));
        mMap.addMarker(new
                MarkerOptions().position(TutorialsPoint).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_icons_pointer_truck)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void showProgress() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                try {
                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    HomeBean.Result data;

    @SuppressLint("SetTextI18n")
    @Override
    public void setDataToView(HomeBean homeBean) {
        data = homeBean.getResult();

    }

    @Override
    public void onResponseFailure() {

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDistanceChangedListener(double myLatitude, double myLongitude) {

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        if (Preferences.getPreferenceValue(context, USER_STATUS).equals("1")) {
            Intent intent;
            if (ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(activity, GT_MY_CURRENT_LOCATION_LATITUDE)) != 0) {
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(activity, GT_MY_CURRENT_LOCATION_LATITUDE)) + "," + ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(activity, GT_MY_CURRENT_LOCATION_LONGITUDE)) + "&daddr=" + 0 + "," + 0));
            } else {
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + 0 + "," + 0 + "&daddr=" + 0 + "," + 0));
            }
            startActivity(intent);
        } else {
            Intent intent;
            if (ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(activity, GT_MY_CURRENT_LOCATION_LATITUDE)) != 0) {
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(activity, GT_MY_CURRENT_LOCATION_LATITUDE)) + "," + ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(activity, GT_MY_CURRENT_LOCATION_LONGITUDE)) + "&daddr=" + 0 + "," + 0));
            } else {
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + 0 + "," + 0 + "&daddr=" + 0 + "," + 0));
            }
            startActivity(intent);
        }
        return false;
    }

}
