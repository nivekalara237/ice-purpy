package com.nivekaa.ecommerce.infra.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.nivekaa.ecommerce.domain.spi.SessionStoragePort;

public class SessionStorage implements SessionStoragePort {
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;
    private static SessionStorage storageInstance;



    public SessionStorage(Context contxt) {
        prefs = contxt.getSharedPreferences("ecommerce-test__prefs", Context.MODE_PRIVATE);
        this.editor = prefs.edit();
    }

    public synchronized static SessionStorage getInstance(Context context) {
        if (storageInstance == null)
            storageInstance = new SessionStorage(context.getApplicationContext());
        return storageInstance;
    }

    @Override
    public void saveDataInt(String attr, int data) {
        this.editor.putInt(attr, data);
        editor.apply();
    }

    @Override
    public void saveDataString(String attr, String data) {
        this.editor.putString(attr, data);
        editor.apply();
    }

    @Override
    public void saveDataBoolean(String attr, boolean data) {
        this.editor.putBoolean(attr, data);
        editor.apply();
    }

    @Override
    public void saveDataFloat(String attr, float data) {
        this.editor.putFloat(attr, data);
        editor.apply();
    }

    @Override
    public void saveDataLong(String attr, long data) {
        this.editor.putLong(attr, data);
        editor.apply();
    }

    @Override
    public void editValueInteger(String KEY, int newValue) {
        this.editor.putInt(KEY, newValue).commit();
    }


    @Override
    public void editValueString(String KEY, String newValue) {
        this.editor.putString(KEY, newValue).commit();
    }

    @Override
    public void editValueFloat(String KEY, float newValue) {
        this.editor.putFloat(KEY, newValue).commit();
    }

    @Override
    public void editValueBoolean(String KEY, boolean newValue) {
        this.editor.putBoolean(KEY, newValue).commit();
    }

    @Override
    public String retrieveDataString(String key) {
        return String.valueOf(this.prefs.getString(key, ""));
    }

    @Override
    public void remove(String key) {
        this.editor.remove(key);
        this.editor.apply();
    }

    @Override
    public int retrieveDataInteger(String key) {
        return this.prefs.getInt(key, 0);
    }

    @Override
    public boolean retrieveDataBoolean(String key) {
        return this.prefs.getBoolean(key, false);
    }

    @Override
    public float retrieveDataFloat(String key) {
        return this.prefs.getFloat(key, 0);
    }

    @Override
    public float retrieveDataLong(String key) {
        return this.prefs.getLong(key, 0);
    }
}
