package com.nivekaa.ecommerce.application;

import android.app.Application;

import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(this)
                .setNotifyImmediately(true)
                .build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);
    }
}
