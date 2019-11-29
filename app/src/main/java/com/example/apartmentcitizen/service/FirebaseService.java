package com.example.apartmentcitizen.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.example.apartmentcitizen.MainActivity;
import com.example.apartmentcitizen.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;


public class FirebaseService extends FirebaseMessagingService {
    public static final String TAG = "NOTIFICATION";


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Firebase service call start");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Data: " + remoteMessage.getData());
        Log.d(TAG, "Intent: " + remoteMessage.toIntent().toString());
        Log.d(TAG, "Message Id: " + remoteMessage.getMessageId());
        Log.d(TAG, "Send Time   : " + remoteMessage.getSentTime());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.d(TAG, "Notification Localization Key: " + remoteMessage.getNotification().getBodyLocalizationKey());
        Log.d(TAG, "Notification Title: " + remoteMessage.getNotification().getTitle());
        Log.d(TAG, "Notification Channel Id: " + remoteMessage.getNotification().getChannelId());

        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "Refreshed token: " + s);
    }

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.logoapartmentlogin)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }

    //
//    private void processGCMReceived(RemoteMessage.Notification notification, Map<String, String> bundleData) {
//        try {
////            if (!BaseApplication.getInstance().isUserBackground()) {
//            String title = bundleData.get(IAppModel.INotifyMessage.TITLE);
//            String message = bundleData.get(IAppModel.INotifyMessage.BODY);
//            //TODO handle click action
//            //bundleData.put(IAppModel.INotifyMessage.CLICK_ACTION, notification.getClickAction());
//            // int badgeNumber = Integer.parseInt(bundleData.get(IAppModel.INotifyMessage.BADGE_NUMBER));
//            handlePushMessage(title, message, bundleData);
////            }
//        } catch (Exception ex) {
//            CommonUtils.createLogFile("error on parse received push");
//            Log.d(TAG, ex.getMessage());
//        }
//    }
//
//    /**
//     * Handle push for account verification.
//     *
//     * @param title   String
//     * @param message String
//     * @param data    Map<String, String>
//     */
//    private void handlePushMessage(String title, String message, Map<String, String> data) {
//        Class clz = SplashActivity.class;
//        Log.i(TAG, "firebase service call start" + "PushNavigationActivity");
//
//        //Check app in background or foreground
//        if (B.getInstance().isAppForeground()) {
//            Log.i(TAG, "firebase service call start" + "isForeground");
//            clz = PushNavigationActivity.class;
//        }
//        if (BaseApplication.getInstance().isUserBackground()) {
//            Log.i(TAG, "firebase service call start" + "isUserBackground");
//            clz = PushNavigationActivity.class;
//        }
//
//
//        Intent itNavigate = new Intent(this, clz);
//
//        Bundle bundleData = new Bundle();
//
//        if (data != null) {
//            //convert map to hash map to push to navigationa activity
//            HashMap<String, String> mapData = new HashMap<>();
//            for (Map.Entry<String, String> entry : data.entrySet()) {
//                mapData.put(entry.getKey(), entry.getValue());
//            }
//            bundleData.putSerializable(IAppModel.INotifyMessage.DATA, mapData);
//            itNavigate.putExtras(bundleData);
//        }
//
//        itNavigate.setAction(UUID.randomUUID().toString());
//
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                itNavigate, PendingIntent.FLAG_ONE_SHOT);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            ContextCompat.startForegroundService(this, itNavigate);
//        }
//
//        showPushNotification(title, message, contentIntent);
//    }
//
//    private void showPushNotification(String title, String msg, PendingIntent contentIntent) {
//
//        try {
//            NotificationManager mNotificationManager = (NotificationManager) this
//                    .getSystemService(Context.NOTIFICATION_SERVICE);
//
//            NotificationCompat.Builder mBuilder;
//            String channelId = "default_id";
//            String channelName = "default";
//
//
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                int importance = NotificationManager.IMPORTANCE_DEFAULT;
//
//                @SuppressLint("WrongConstant")
//                NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
//                mNotificationManager.createNotificationChannel(notificationChannel);
//                mBuilder = new NotificationCompat.Builder(getApplicationContext(), notificationChannel.getId());
//            } else {
//                mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId);
//            }
//
//
//            mBuilder.setSmallIcon(R.drawable.common_google_signin_btn_icon_light_normal_background)
//                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
//                    .setContentText(msg);
//
//            mBuilder.setContentIntent(contentIntent);
//            mBuilder.setAutoCancel(true);
//            mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                mBuilder.setColor(Color.WHITE);
//            }
//
//            if (TextUtils.isEmpty(title)) {
//                mBuilder.setContentTitle(this.getString(R.string.app_name));
//            } else {
//                mBuilder.setContentTitle(title);
//            }
//
//            int pushId = (int) System.currentTimeMillis();
//            mNotificationManager.notify(pushId, mBuilder.build());
//
//            // Get instance of Vibrator from current Context
//            if (checkVibrationIsOn(getApplicationContext())) {
//                Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
//
//                // Vibrate for 400 milliseconds
//                v.vibrate(400);
//            }
//
//            // This is for bladge on home icon
//
//        } catch (Exception ex) {
//            Log.e(TAG, ex.getMessage());
//        }
//    }
//
//    public boolean checkVibrationIsOn(Context context) {
//
//        Boolean isVibrate = false;
//
//        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//
//        switch (audio.getRingerMode()) {
//            case AudioManager.RINGER_MODE_NORMAL:
//                break;
//            case AudioManager.RINGER_MODE_SILENT:
//                break;
//            case AudioManager.RINGER_MODE_VIBRATE:
//                isVibrate = true;
//                break;
//        }
//
//        return isVibrate;
//    }
}