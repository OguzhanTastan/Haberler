package com.example.haberler;

import android.util.Log;
import android.widget.Toast;

public class AppLogger {

    public static final String TAG = "logger";

    public static void log(String str) {
        Log.i(TAG, "log: " + str);
        AppUtil.showToast(str);
    }

    public static void err(Throwable throwable) {
        Log.e(TAG, "log: " + throwable.getMessage(), throwable);
        AppUtil.showToast(throwable.getMessage());
    }

}
