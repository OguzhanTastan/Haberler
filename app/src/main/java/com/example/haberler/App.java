package com.example.haberler;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

public class App extends MultiDexApplication {

    private static Context ctx;

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this;
    }

    public static Context getCtx() {
        return ctx;
    }
}
