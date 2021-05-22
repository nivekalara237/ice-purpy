package com.nivekaa.ecommerce.domain.spi;

public interface SessionStoragePort {

    void saveDataInt(String attr, int data);

    void saveDataString(String attr, String data);

    void saveDataBoolean(String attr, boolean data);

    void saveDataFloat(String attr, float data);

    void saveDataLong(String attr, long data);

    void editValueInteger(String KEY, int newValue);

    void editValueString(String KEY, String newValue);

    void editValueFloat(String KEY, float newValue);

    void editValueBoolean(String KEY, boolean newValue);

    String retrieveDataString(String key);

    void remove(String key);

    int retrieveDataInteger(String key);

    boolean retrieveDataBoolean(String key);

    float retrieveDataFloat(String key);

    float retrieveDataLong(String key);
}
