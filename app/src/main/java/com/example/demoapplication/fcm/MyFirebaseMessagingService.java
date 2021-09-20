package com.example.demoapplication.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.example.demoapplication.ui.activity.MainActivity;
import com.example.demoapplication.R;
import com.example.demoapplication.RestAPI.AppConstants;
import com.example.demoapplication.utils.Preferences;

import static com.example.demoapplication.RestAPI.AppConstants.USERID;
import static com.example.demoapplication.RestAPI.AppConstants.USER_STATUS;

public class MyFirebaseMessagingService extends FirebaseMessagingService implements KeyEvent.Callback {

    private Context mContext;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    public static MediaPlayer mediaPlayer;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e("TokenService", s);
        AppConstants.tokan = s;
    }

    private static int notificationCount = 0;

    @Override
    public void handleIntent(Intent intent) {
        Log.d("FCM", "handleIntent");
        mContext = getApplicationContext();

        //if you don't know the format of your FCM message,
        //just print it out, and you'll know how to parse it
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                Log.d("FCM", "Key: " + key + " Value: " + value);
            }
        }

        //the background notification is created by super method
        //but you can't remove the super method.
        //the super method do other things, not just creating the notification
        super.handleIntent(intent);

        //remove the Notificaitons
        removeFirebaseOrigianlNotificaitons();

        if (bundle == null)
            return;

        String title = null, msg = null;

        //if the message is sent from Firebase platform, the key will be that
        msg = (String) bundle.get("gcm.notification.body");

        if (bundle.containsKey("gcm.notification.title"))
            title = (String) bundle.get("gcm.notification.title");

        //parse your custom message
        String testValue = null;
        testValue = (String) bundle.get("testKey");
        notificationCount = notificationCount++;
        String id = notificationCount + "";
        if (bundle.containsKey("noti_id"))
            id = (String) bundle.get("noti_id");

        String user_status = Preferences.getPreferenceValue(getApplicationContext(), USER_STATUS);

        if (title != null)
            if (!title.trim().equals("")) {
                if (user_status.equals("0") || user_status.equals("NA")) {
                    createNotification(title, msg, id, bundle);
                }
            }
        //////////////////////////////////////////////////////////////////////////////////////
        Log.d("PRINT", "HELLO");
        mContext = getApplicationContext();
        Log.e("getpushdata", "" + title);
        Log.e("getpushdata", "" + msg);

        String user_id = Preferences.getPreferenceValue(mContext, USERID);
        if (user_status.equals("0") || user_status.equals("NA")) {
            if (!user_id.isEmpty()) {
                Log.e("getpushdata", "user_id " + user_id);
                try {
                    String noti_type = "";
                    if (bundle.containsKey("noti_type"))
                        noti_type = (String) bundle.get("noti_type");

                    if (noti_type.equalsIgnoreCase("book_request")) {
                        Log.e("getpushdata", "user_id " + user_id);
                    }

                } catch (Exception e) {

                    Log.d("PRINT", e.toString());
                }
            }
        }
///////////////////////////////////////////////////////////////////
    }

    /**
     * remove the notification created by "super.handleIntent(intent)"
     */
    private void removeFirebaseOrigianlNotificaitons() {
        //check notificationManager is available
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null)
            return;
        //check api level for getActiveNotifications()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //if your Build version is less than android 6.0
            //we can remove all notifications instead.
            //notificationManager.cancelAll();
            return;
        }
        //check there are notifications
        StatusBarNotification[] activeNotifications =
                notificationManager.getActiveNotifications();
        if (activeNotifications == null)
            return;
        //remove all notification created by library(super.handleIntent(intent))
        for (StatusBarNotification tmp : activeNotifications) {
            Log.d("FCM StatusBarNotific",
                    "tag/id: " + tmp.getTag() + " / " + tmp.getId());
            String tag = tmp.getTag();
            int id = tmp.getId();

            //trace the library source code, follow the rule to remove it.
            if (tag != null)
                if (tag.contains("FCM-Notification"))
                    notificationManager.cancel(tag, id);
        }
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

    }

    public void createNotification(String title, String message, String senderId, Bundle bundle) {
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + mContext.getPackageName() + "/" + R.raw.test_audio); //Here is FILE_NAME is the name of file that you want to play

        Intent resultIntent = new Intent(mContext, MainActivity.class);
        Bundle extras = new Bundle();
        try {
            String noti_type = "";
            if (bundle.containsKey("noti_type"))
                noti_type = (String) bundle.get("noti_type");

            if (noti_type.equalsIgnoreCase("book_request")) {
                extras.putString("noti_type", (String) bundle.get("noti_type"));
                if (bundle.containsKey("noti_id"))
                    extras.putString("noti_id", (String) bundle.get("noti_id"));
                if (bundle.containsKey("noti_book_id"))
                    extras.putString("noti_book_id", (String) bundle.get("noti_book_id"));
                if (bundle.containsKey("org_name"))
                    extras.putString("org_name", (String) bundle.get("org_name"));
                if (bundle.containsKey("org_address"))
                    extras.putString("org_address", (String) bundle.get("org_address"));
                if (bundle.containsKey("cust_address"))
                    extras.putString("cust_address", (String) bundle.get("cust_address"));
                if (bundle.containsKey("cust_name"))
                    extras.putString("cust_name", (String) bundle.get("cust_name"));
                if (bundle.containsKey("cust_lat"))
                    extras.putString("cust_lat", (String) bundle.get("cust_lat"));
                if (bundle.containsKey("cust_long"))
                    extras.putString("cust_long", (String) bundle.get("cust_long"));
                if (bundle.containsKey("org_lat"))
                    extras.putString("org_lat", (String) bundle.get("org_lat"));
                if (bundle.containsKey("org_long"))
                    extras.putString("org_long", (String) bundle.get("org_long"));

            }
        } catch (Exception e) {

            Log.d("PRINT", e.toString());
        }

        String str = Preferences.getPreferenceValue(mContext, "pendingBookinItems");

        extras.putString("name", title);
        extras.putString("chat_msg", message);
        extras.putString("senderId", senderId);
        resultIntent.putExtra("notificat", extras);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_ONE_SHOT);

        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(R.drawable.noti_logo);
        mBuilder.setColor(mContext.getResources().getColor(R.color.blue));
        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(mContext, sound);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Notification notification = mBuilder.build();
        assert mNotificationManager != null;
        mNotificationManager.notify(0 /* Request Code */, notification);
    }

    @Override
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onKeyMultiple(int i, int i1, KeyEvent keyEvent) {
        return false;
    }

}

