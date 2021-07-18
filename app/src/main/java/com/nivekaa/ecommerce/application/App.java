package com.nivekaa.ecommerce.application;

import android.app.Application;

import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;

import java.util.HashMap;
import java.util.Map;

import sdk.pendo.io.Pendo;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String pendoAppKey = "eyJhbGciOiJSUzI1NiIsImtpZCI6IiIsInR5cCI6IkpXVCJ9.eyJkYXRhY2VudGVyIjoidXMiLCJrZXkiOiI4N2VkOThkNjkxNTk2Mzg4ZmMwOGNiNDliMDMyZWE3YzEzYzYzNzQyMmE1M2IyNzllYzVkZGNkODU2OGQzYWM3YmRlN2JhZjg0M2EzNTIzNmI3ZjM0YmRlY2M3ZDJjYTQ3ZGU4YTk1YWRkNTY4YzliNGVlNWRiNmMxM2YzN2Q0MDc4Y2I4YWJiMDkxZWE4M2U4YzliOTA3NzEzMjY1MjM2LjgxYTRiYWNlYTJiZGJmMTJjZDg3MzM5OWZiYjIxNjcwLmZkOGU3NTI2ZmM3MjU2MTVjM2Q0NTExYmMyN2VmMjNkOWVlODAzMjA2ZTdiOThmOGUwY2M3MmUwYjQzZTAyZDYifQ.K7Psx-2i6wIrgA1zDtWqwJ3aPAw-4emtuw_w--4ja8oe3nzrlPtrMr5nSmHEf6lLDvO8BwztqZjyC_3VFlWvX8GydXxBVzioqUGZIQL_tfKFvMDR6yqRVLtjyJg51WnPcH5t6Tw1XHDu_R-fpoVkiEYcUXs5DLP3YqKff53HiZc";
        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(this)
                .setNotifyImmediately(true)
                .build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);

        Pendo.PendoInitParams pendoParams = new Pendo.PendoInitParams();
        //pendoParams.setPendoOptions(new Pendo.PendoOptions.Builder().setEnvironmentDebugOnly("DEV").build());
        pendoParams.setVisitorId("John Smith");
        pendoParams.setAccountId("Acme Inc");

        //send Visitor Level Data
        Map<String, Object> userData = new HashMap<>();
        userData.put("age", "27");
        userData.put("country", "USA");
        pendoParams.setUserData(userData);

        //send Account Level Data
        Map<String, Object> accountData = new HashMap<>();
        accountData.put("Tier", "1");
        accountData.put("Size", "Enterprise");
        pendoParams.setAccountData(accountData);

        Pendo.initSDK(this, pendoAppKey, null);

    }
}
