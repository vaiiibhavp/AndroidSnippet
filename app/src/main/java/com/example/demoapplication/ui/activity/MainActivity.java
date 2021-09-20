package com.example.demoapplication.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.demoapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.example.demoapplication.databinding.ActivityMainBinding;
import com.example.demoapplication.fcm.MyFirebaseMessagingService;
import com.example.demoapplication.ui.fragment.HomeMapFragment;
import com.example.demoapplication.utils.Preferences;
import com.example.demoapplication.utils.ProjectUtils;

import java.util.List;

import static com.example.demoapplication.RestAPI.AppConstants.USERID;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    public static BottomNavigationView navigationView;
    public static MainActivity activity;
    private static final int loc_request_code = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        activity = this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        loadFragment(new HomeMapFragment());
        homeFrag = true;
        navigationView = mainBinding.includeDash.navigation;

        FirebaseCrashlytics.getInstance().setCustomKey(USERID, Preferences.getPreferenceValue(this, USERID));

        mainBinding.includeDash.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                homeFrag = false;
                switch (item.getItemId()) {
                    case R.id.home:
                        homeFrag = true;
                        fragment = new HomeMapFragment();
                        break;
                    case R.id.myride:
                        break;
                    case R.id.notification:
                        break;
                    case R.id.docs:
                        break;
                    case R.id.profile:
                        break;
                }
                return loadFragment(fragment);
            }
        });

        if (Preferences.getPreferenceValueInt(this, "newnotification") > 0) {
            showBadge();
        } else {
            removeBadge();
        }

    }

    private void stopMediaPlayer() {
        if (MyFirebaseMessagingService.mediaPlayer != null) {
            try {
                if (MyFirebaseMessagingService.mediaPlayer.isPlaying()) {
                    MyFirebaseMessagingService.mediaPlayer.stop();
                    MyFirebaseMessagingService.mediaPlayer.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            stopMediaPlayer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1234 == requestCode) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted
//                Loc_permissiongranted = true;
                LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

                boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (isNetworkEnabled) {
                    //check the network permission
                    checkPermissionRequests();
                }
            } else {
                //show some warning
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void checkPermissionRequests() {

        if (ContextCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                if (!Preferences.getPreferenceValueBoolean(activity, "autostart_show")) {
                    Preferences.setPreferenceValue(activity, "autostart_show", true);
                    new AlertDialog.Builder(this)
                            .setTitle("DMS Autostart")
                            .setMessage("You need to enable the autostart permission. If you ignore this time when you have to do it manually.")
                            .setPositiveButton("Open", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
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
                                                List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                                                if (list.size() > 0) {
                                                    startActivity(intent);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            })
                            .create()
                            .show();
                }
            }

        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            Log.i("", "");
            new AlertDialog.Builder(this)
                    .setTitle("Location permission")
                    .setMessage("you need to allow location permission")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(MainActivity.this, ProjectUtils.getPermissionArray(),
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
    protected void onDestroy() {
        super.onDestroy();
    }


    public void showBadge() {
        removeBadge();
        BottomNavigationItemView itemView = navigationView.findViewById(R.id.notification);
        View badge = LayoutInflater.from(itemView.getContext()).inflate(R.layout.notification_badge, navigationView, false);
        TextView text = badge.findViewById(R.id.notifications_badge);
        text.setText(Preferences.getPreferenceValueInt(activity, "newnotification") + "");
        itemView.addView(badge);
    }

    public void removeBadge() {
        BottomNavigationItemView itemView = navigationView.findViewById(R.id.notification);
        if (itemView.getChildCount() == 3) {
            try {
                itemView.removeViewAt(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            try {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }


    public static boolean homeFrag = true;

    @Override
    public void onBackPressed() {
        // Close navigation drawer if open

        // If not in Home, move back to Home.
        if (!homeFrag) {
            homeFrag = true;
            mainBinding.includeDash.navigation.setSelectedItemId(R.id.home);
            loadFragment(new HomeMapFragment());
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Exit Application?");
            alertDialogBuilder
                    .setMessage("Click yes to exit!")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    moveTaskToBack(true);
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                }
                            })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }
}
