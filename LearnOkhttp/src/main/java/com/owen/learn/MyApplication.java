package com.owen.learn;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Owen on 2016/4/20.
 */
public class MyApplication extends Application{
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
