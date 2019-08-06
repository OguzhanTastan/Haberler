package com.example.haberler.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    public void onMessageReceived(RemoteMessage remoteMassage) {
        if(remoteMassage.getData().size() >0) {
            Log.d(TAG, ""+remoteMassage.getData());

        }

    }



}
