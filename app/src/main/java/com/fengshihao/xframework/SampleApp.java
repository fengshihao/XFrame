package com.fengshihao.xframework;

import android.app.Application;
import android.util.Log;

import com.fengshihao.xframe.XFrame;

public class SampleApp extends Application {
    private static final String TAG = "SampleApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: create  SampleApp");

        XFrame xFrame = XFrame.getInstance();
    }
}
