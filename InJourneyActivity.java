package com.prometteur.dmsdriver.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lin.timeline.TimeLineDecoration;
import com.prometteur.dmsdriver.R;
import com.prometteur.dmsdriver.databinding.ActivityInJourneyMapBinding;
import com.prometteur.dmsdriver.interfaces.DistanceTimeChangedListener;
import com.prometteur.dmsdriver.mvp.model.RideDetailsContract;
import com.prometteur.dmsdriver.mvp.model.RideDetailsMobel;
import com.prometteur.dmsdriver.mvp.model.jsonmodel.RideDetailsBean;
import com.prometteur.dmsdriver.mvp.presenter.RideDetailsPresenter;
import com.prometteur.dmsdriver.service.location_services.GetRequiredTimeService;
import com.prometteur.dmsdriver.ui.adapter.RideDetailsAdapter;
import com.prometteur.dmsdriver.utils.Preferences;
import com.prometteur.dmsdriver.utils.ProjectUtils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.lin.timeline.TimeLineDecoration.BEGIN;
import static com.lin.timeline.TimeLineDecoration.END;
import static com.lin.timeline.TimeLineDecoration.NORMAL;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.GRTS_DISTANCE_FROM_CUST;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.GRTS_TIME_FOR_CUST;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.GT_MY_CURRENT_LOCATION_LATITUDE;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.GT_MY_CURRENT_LOCATION_LONGITUDE;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.ORGID;
import static com.prometteur.dmsdriver.RestAPI.AppConstants.USER_STATUS;

public class InJourneyActivity extends AppCompatActivity implements OnMapReadyCallback, RideDetailsContract.RideDetailsView, /*LocationListener, */DistanceTimeChangedListener {
    ActivityInJourneyMapBinding journeyMapBinding;
    public static AppCompatActivity activity;
    RideDetailsPresenter rideDetailsPresenter;
    ProgressDialog progressDialog;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        journeyMapBinding = ActivityInJourneyMapBinding.inflate(getLayoutInflater());
        setContentView(journeyMapBinding.getRoot());
        activity = this;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        rideDetailsPresenter = new RideDetailsPresenter(this);
        rideDetailsPresenter.requestDataFromServer(Preferences.getPreferenceValue(InJourneyActivity.this, "bookId"), Preferences.getPreferenceValue(InJourneyActivity.this, ORGID));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        journeyMapBinding.relBottomCard.setVisibility(View.GONE);
        journeyMapBinding.tvArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (journeyMapBinding.relBottomCard.getVisibility() == View.GONE) {
                    journeyMapBinding.relBottomCard.setVisibility(View.VISIBLE);
                    journeyMapBinding.tvArrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                } else {
                    journeyMapBinding.relBottomCard.setVisibility(View.GONE);
                    journeyMapBinding.tvArrow.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                }
            }
        });
        journeyMapBinding.ivBack.setOnClickListener(view -> finish());
        journeyMapBinding.btnFinish.setOnClickListener(view -> {
            getDistanceFromCustomer();
            if (data != null) {
                startActivity(new Intent(activity, ReachingCustomerDialogActivity.class).putExtra("obj", (Serializable) data));
            }
        });

        journeyMapBinding.tvLoadingSlipDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InJourneyActivity.this, LoadingMaterialActivity.class));
            }
        });

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkEnabled) {

            if (ContextCompat.checkSelfPermission(
                    activity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    activity, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                {
                    ActivityCompat.requestPermissions((Activity) activity,
                            ProjectUtils.getPermissionArray(), 1234);
                }
            }
        }
    }

    GoogleMap mMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng TutorialsPoint = new LatLng(ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(getApplicationContext(), GT_MY_CURRENT_LOCATION_LATITUDE)), ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(getApplicationContext(), GT_MY_CURRENT_LOCATION_LONGITUDE)));
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(activity, R.raw.map_style));
        mMap.addMarker(new
                MarkerOptions().position(TutorialsPoint).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_icons_pointer_truck)).title("Tutorialspoint.com"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(TutorialsPoint, 14));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Preferences.getPreferenceValue(this, USER_STATUS).equals("1")) {
            finish();
        }
        GetRequiredTimeService.distanceTimeChangedListener = this;
        if (data != null)
            if (data.getBookId() != null) {
                if (!data.getBookId().trim().equals("")) {
                    journeyMapBinding.llBotttomview.setVisibility(View.VISIBLE);
                } else journeyMapBinding.llBotttomview.setVisibility(View.GONE);
            } else journeyMapBinding.llBotttomview.setVisibility(View.GONE);
        getDistanceFromCustomer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GetRequiredTimeService.distanceTimeChangedListener = null;
    }

    @Override
    public void showProgress() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    RideDetailsBean.Data data;

    @Override
    public void setDataToView(RideDetailsBean rideDetailsBean) {
        data = rideDetailsBean.getResult().getData();
        journeyMapBinding.relBottomCard.setVisibility(View.VISIBLE);
        journeyMapBinding.tvBookingId.setText(" B-" + data.getBookNo());
        journeyMapBinding.tvRideNumber.setText(" R-" + data.getBookRideNo());
        if (data != null) {
            if (data.getBookId() != null) {
                if (!data.getBookId().trim().equals("")) {
                    journeyMapBinding.llBotttomview.setVisibility(View.VISIBLE);
                } else journeyMapBinding.llBotttomview.setVisibility(View.GONE);
            } else journeyMapBinding.llBotttomview.setVisibility(View.GONE);
            journeyMapBinding.tvKm.setText("0.0 KM");
            journeyMapBinding.tvHrs.setText("0 min");
            getDistanceFromCustomer();
            journeyMapBinding.rvRide.setLayoutManager(new
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            List<RideDetailsMobel> rideDetailsMobels = new ArrayList<>();
            rideDetailsMobels.add(new
                    RideDetailsMobel(data.getOrgName(), data.
                    getOrgAddress(), data.
                    getBookDepartureTime()));
            rideDetailsMobels.add(new
                    RideDetailsMobel(data.getCustName(), data.
                    getBookCustLocation(), ""));
            final RideDetailsAdapter rideDetailsAdapter = new RideDetailsAdapter(this, rideDetailsMobels);
            journeyMapBinding.rvRide.setAdapter(rideDetailsAdapter);
            final TimeLineDecoration decoration = new TimeLineDecoration(this)
                    .setLineColor(android.R.color.black)
                    .setLineWidth(1)
                    .setLeftDistance(83)
                    .setTopDistance(16)
                    .setBeginMarker(R.drawable.ic_green_dot)
                    .setBeginMarkerRadius(4)
                    .setMarkerRadius(4)
                    .setMarkerColor(R.color.red)
                    .setCallback(new TimeLineDecoration.TimeLineAdapter() {//or new TimeLineDecoration.TimeLineCallback
                        @Override
                        public int getTimeLineType(int position) {
                            if (position == 0) return BEGIN;
                            else if (position == rideDetailsAdapter.getItemCount() - 1) return END;
                            else return NORMAL;
                        }
                    });
            journeyMapBinding.rvRide.addItemDecoration(decoration);
            LatLng TutorialsPoint = new LatLng(ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(getApplicationContext(), GT_MY_CURRENT_LOCATION_LATITUDE)), ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(getApplicationContext(), GT_MY_CURRENT_LOCATION_LONGITUDE)));
            if (mMap != null) {
                mMap.clear();
                mMap.addMarker(new
                        MarkerOptions().position(TutorialsPoint).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_icons_pointer_truck))/*.title("Tutorialspoint.com")*/);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(TutorialsPoint, 14));
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        Intent intent;
                        if (ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(getApplicationContext(), GT_MY_CURRENT_LOCATION_LATITUDE)) != 0) {
                            intent = new Intent(android.content.Intent.ACTION_VIEW,
                                    Uri.parse("http://maps.google.com/maps?saddr=" + ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(getApplicationContext(), GT_MY_CURRENT_LOCATION_LATITUDE)) + "," + ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(getApplicationContext(), GT_MY_CURRENT_LOCATION_LONGITUDE)) + "&daddr=" + data.getCustLat() + "," + data.getCustLong()));
                        } else {
                            intent = new Intent(android.content.Intent.ACTION_VIEW,
                                    Uri.parse("http://maps.google.com/maps?saddr=" + data.getOrgLat() + "," + data.getOrgLong() + "&daddr=" + data.getCustLat() + "," + data.getCustLong()));
                        }
                        startActivity(intent);
                        return false;
                    }
                });
            }
        }
    }

    @Override
    public void onResponseFailure() {
    }

    private void getDistanceFromCustomer() {
        ProjectUtils.checkLanguage(InJourneyActivity.this);
        double distanceInKm = 0;
        if (Preferences.getPreferenceValueLong(getApplicationContext(), GRTS_DISTANCE_FROM_CUST) != 0) {
            try {
                distanceInKm = Preferences.getPreferenceValueLong(getApplicationContext(), GRTS_DISTANCE_FROM_CUST) / 1000.0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String distanceAway = "0km";
        if (distanceInKm > 0) {
            String d = new DecimalFormat("##.##").format(distanceInKm);
            distanceAway = d + "KM";
        } else {
            String d = new DecimalFormat("##.##").format(Preferences.getPreferenceValueLong(getApplicationContext(), GRTS_DISTANCE_FROM_CUST));
            distanceAway = d + "M";
        }
        if (!distanceAway.equals("0M"))
            journeyMapBinding.tvKm.setText("" + distanceAway);
        if (Preferences.getPreferenceValue(getApplicationContext(), GRTS_TIME_FOR_CUST) != null) {
            if (!Preferences.getPreferenceValue(getApplicationContext(), GRTS_TIME_FOR_CUST).equalsIgnoreCase("0")) {
                journeyMapBinding.tvHrs.setText(Preferences.getPreferenceValue(getApplicationContext(), GRTS_TIME_FOR_CUST));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDistanceChangedListener(double myLatitude, double myLongitude) {
        runOnUiThread(() -> {
            if (data != null) {
                getDistanceFromCustomer();
                LatLng TutorialsPoint = new LatLng(myLatitude, myLongitude);
                if (mMap != null) {
                    mMap.clear();
                    mMap.addMarker(new
                            MarkerOptions().position(TutorialsPoint).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_icons_pointer_truck))/*.title("Tutorialspoint.com")*/);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(TutorialsPoint, 14));
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(@NonNull Marker marker) {
                            Intent intent;
                            if (ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(getApplicationContext(), GT_MY_CURRENT_LOCATION_LATITUDE)) != 0) {
                                intent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("http://maps.google.com/maps?saddr=" + ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(getApplicationContext(), GT_MY_CURRENT_LOCATION_LATITUDE)) + "," + ProjectUtils.getDoubleFromString(Preferences.getPreferenceValue(getApplicationContext(), GT_MY_CURRENT_LOCATION_LONGITUDE)) + "&daddr=" + data.getCustLat() + "," + data.getCustLong()));
                            } else {
                                intent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("http://maps.google.com/maps?saddr=" + data.getOrgLat() + "," + data.getOrgLong() + "&daddr=" + data.getCustLat() + "," + data.getCustLong()));
                            }
                            startActivity(intent);
                            return false;
                        }
                    });
                }
            }
        });
    }
}
