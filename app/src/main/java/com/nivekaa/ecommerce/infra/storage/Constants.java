package com.nivekaa.ecommerce.infra.storage;

import com.nivekaa.ecommerce.infra.storage.entry.OrderEntity;
import com.nivekaa.ecommerce.infra.storage.entry.ProductEntity;
import com.thedeanda.lorem.LoremIpsum;

public class Constants {
    public static final String SQL_CREATE_PRODUCT_ENTRY =
            "CREATE TABLE " + ProductEntity.TABLE_NAME + " (" +
                    ProductEntity.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    ProductEntity.COLUMN_NAME_NAME + " TEXT," +
                    ProductEntity.COLUMN_NAME_UUID + " TEXT," +
                    ProductEntity.COLUMN_NAME_RATE + " FLOAT," +
                    ProductEntity.COLUMN_NAME_PRICE + " FLOAT," +
                    ProductEntity.COLUMN_NAME_DISCOUNT + " FLOAT," +
                    ProductEntity.COLUMN_NAME_OLD_PRICE + " FLOAT," +
                    ProductEntity.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    ProductEntity.COLUMN_NAME_CATEGORY + " TEXT," +
                    ProductEntity.COLUMN_NAME_IMAGE + " TEXT)";

    public static final String SQL_CREATE_ORDER_ENTRY = "CREATE TABLE " + OrderEntity.TABLE_NAME + " (" +
                    OrderEntity.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    OrderEntity.COLUMN_NAME_PRODUCT_UUID + " INTEGER," +
                    OrderEntity.COLUMN_NAME_QTE + " INTEGER,"+
                    "FOREIGN KEY("+ OrderEntity.COLUMN_NAME_PRODUCT_UUID +") REFERENCES " +
                    ProductEntity.TABLE_NAME + "("+ ProductEntity.COLUMN_NAME_ID +"));";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ProductEntity.TABLE_NAME + ";" +
                    "DROP TABLE IF EXISTS " + OrderEntity.TABLE_NAME + ";";

    public static final LoremIpsum Lorem = LoremIpsum.getInstance();
}
