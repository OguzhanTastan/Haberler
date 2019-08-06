package com.example.haberler.service;


import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;


import static androidx.constraintlayout.widget.Constraints.TAG;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseIIDService";

    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getInstanceId().getResult().getToken();
        Log.d(TAG, "Token" + token);
        sendRegistrationToServer(token);

    }

    private void sendRegistrationToServer(String token) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("server/saving-data/IDs");
        ref.push().setValue(token);


    }
}
