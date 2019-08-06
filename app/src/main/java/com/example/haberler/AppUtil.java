package com.example.haberler;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.PreferenceManager;


public class AppUtil {

    public static void showToast(final String str) {
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        final Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(App.getCtx(), str, Toast.LENGTH_SHORT).show();
            }
        };
        mainHandler.post(myRunnable);
    }

    public static float getTextSize() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getCtx());
        String textSize = sharedPreferences.getString("text_size", "Medium");
        if (textSize.equalsIgnoreCase("small")) return 0.8f;
        else if (textSize.equalsIgnoreCase("large")) return 1.1f;
        else return 1f;
    }

}
