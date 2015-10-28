package org.yi01.kakeibo;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class KakeiboApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
