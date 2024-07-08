package com.example.clpaas_frontend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Date;

public class Clpaas_Receiver extends BroadcastReceiver {
    private static final String TAG = "Clpaas_Receiver";

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        // 인텐트로부터 SMS 메시지를 추출하고 로그에 기록
//        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
//            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
//            for (Object pdu : pdus) {
//                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
//                String sender = smsMessage.getDisplayOriginatingAddress();
//                String message = smsMessage.getDisplayMessageBody();
//                Log.d(TAG, "보낸 사람 : " + sender + ", 메시지 : " + message);
//
//                // 피싱 및 스팸 메시지인지 분석하는 로직 추가
//                // analyzeMessage(sender, message);
//            }
//        }
//    }
//
//    // 피싱 및 스팸 메시지 분석하는 메서드
//    private void analyzeMessage(String sender, String message) {
//        // 분석 로직 구현
//        if (message.contains("피싱") || message.contains("스팸")) {
//            Log.d(TAG, "피싱 또는 스팸 메시지로 판별됨");
//        } else {
//            Log.d(TAG, "정상 메시지로 판별됨");
//        }
//    }
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() called");

        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = parseSmsMessage(bundle);

        if(messages.length > 0){
            String sender = messages[0].getOriginatingAddress();
            String content = messages[0].getMessageBody().toString();
            Date date = new Date(messages[0].getTimestampMillis());

            Log.d(TAG, "보낸 사람:" + sender);
            Log.d(TAG, "내용:" + content);
            Log.d(TAG, "날짜:" + date);
        }
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle){
        //PDU: Protocol Date Units
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        for(int i=0; i< objs.length; i++){
            messages[i] = SmsMessage.createFromPdu((byte[])objs[i]);
        }
        return messages;
    }
}