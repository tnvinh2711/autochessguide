package com.zinzin.tierbuilder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zinzin.tierbuilder.utils.Utils;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("MyFirebaseMessaging", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.e("My Token",token);
                    }
                });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 959,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            channel = new NotificationChannel("222", "my_channel", NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
        }
        Drawable ico = this.getApplicationInfo().loadIcon(this.getPackageManager());
        Bitmap bitmap = Utils.drawableToBitmap(ico);
        int bitmapSize = (int) Math.min(64 * getDensity(this), bitmap.getWidth());
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        getApplicationContext(), "222")
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setAutoCancel(true)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setSound(soundUri)
                        .setPriority(1)
                        .setLargeIcon(Bitmap.createScaledBitmap(bitmap, bitmapSize, bitmapSize, false))
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(resultPendingIntent)
                ;

        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        nm.notify(101, builder.build());
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
}