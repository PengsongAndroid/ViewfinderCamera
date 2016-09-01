package com.peng.camera;

import android.app.Application;

/**
 * Created by PS on 2016/9/1.
 */
public class MyApplication extends Application{

    public static final String TAG = "MyApplication";

    private static MyApplication instance = null;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
