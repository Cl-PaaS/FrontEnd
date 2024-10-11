package com.example.clpaas_frontend;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsMessage;

import android.util.Log;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.util.Date;

public class Clpaas_Receiver extends BroadcastReceiver {
    private static final String TAG = "Clpaas_Receiver"; //for log output
    private static final String CHANNEL_ID = "sms_channel"; //definition of alarm channel

    String content;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() called");

        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);

//        if (messages.length > 0) {
////            String sender = messages[0].getOriginatingAddress();
////            content = messages[0].getMessageBody().toString();
//            String sender = intent.getStringExtra("sender");
//            String content = intent.getStringExtra("message_content");
//            Date date = new Date(messages[0].getTimestampMillis());
//
//            Log.d("Clpaas_Receiver", "보낸 사람: " + sender);
//            Log.d("Clpaas_Receiver", "내용: " + content);

        if (messages.length > 0) {
            String sender = messages[0].getOriginatingAddress();
            String content = messages[0].getMessageBody();
            Date date = new Date(messages[0].getTimestampMillis());

            Log.d(TAG, "보낸 사람: " + sender);
            Log.d(TAG, "내용: " + content);


            Intent mainIntent = new Intent(context, MainActivity.class);
            mainIntent.putExtra("message_content", content);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainIntent);

            createNotificationChannel(context);
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                sendNotification(context, sender, content);
            } else {
                Log.d(TAG, "알림 권한이 없습니다.");
                // 권한 요청
                requestNotificationPermission(context);
            }
        }
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        //PDU: Protocol Data Units
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        for (int i = 0; i < objs.length; i++) {
            messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
        }
        return messages;
    }

    private void createNotificationChannel(Context context) {
        // 알림 채널을 생성 (Android 8.0 이상)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "SMS Channel";
            String description = "Channel for SMS notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @SuppressLint("NotificationPermission")
    private void sendNotification(Context context, String sender, String message) {
        // 문자 알림 생성
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("message_content", content);  // content를 인텐트에 추가
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_sms_notification) // 알림 아이콘
                .setContentTitle("보낸 사람 : " + sender) // 알림 제목
                .setContentText(message) // 알림 내용
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message)) // 긴 메시지 스타일
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // 알림 우선순위
                .setContentIntent(pendingIntent) // 알림 클릭 시 이동할 인텐트
                .setAutoCancel(true); // 클릭 시 알림 자동 삭제

        try {
            // 알림을 표시
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1, builder.build());
        } catch (SecurityException e) {
            Log.e(TAG, "알림 권한이 거부되었습니다.", e);
        }
    }

    private void requestNotificationPermission(Context context) {
        if (context instanceof MainActivity) {
            ActivityCompat.requestPermissions((MainActivity) context, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }
}
