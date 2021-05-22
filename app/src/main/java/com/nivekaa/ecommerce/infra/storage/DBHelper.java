package com.nivekaa.ecommerce.infra.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.nivekaa.ecommerce.domain.model.CategoryType;
import com.nivekaa.ecommerce.domain.model.OrderItemVM;
import com.nivekaa.ecommerce.domain.model.ProductVM;
import com.nivekaa.ecommerce.infra.storage.entry.OrderEntity;
import com.nivekaa.ecommerce.infra.storage.entry.ProductEntity;
import com.nivekaa.ecommerce.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static com.nivekaa.ecommerce.infra.storage.Constants.Lorem;
import static com.nivekaa.ecommerce.infra.storage.Constants.SQL_CREATE_ORDER_ENTRY;
import static com.nivekaa.ecommerce.infra.storage.Constants.SQL_CREATE_PRODUCT_ENTRY;
import static com.nivekaa.ecommerce.infra.storage.Constants.SQL_DELETE_ENTRIES;

public class DBHelper extends SQLiteOpenHelper {
    public static DBHelper dbHelperInstance;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "__nick_shopping__.db";
    private SQLiteDatabase currentDB = null;

    public synchronized static DBHelper getInstance(Context context) {
        if (dbHelperInstance == null) {
            dbHelperInstance = new DBHelper(context.getApplicationContext());
        }
        return dbHelperInstance;
    }

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCT_ENTRY);
        db.execSQL(SQL_CREATE_ORDER_ENTRY);
        currentDB = db;
        initData();
        currentDB = null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldv, int newVersion) {
        onUpgrade(db, oldv, newVersion);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        if (currentDB == null)
            return super.getWritableDatabase();
        else
            return currentDB;
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        if (currentDB == null)
            return super.getWritableDatabase();
        else
            return currentDB;
    }

    public void addProduct(ProductVM product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(ProductEntity.COLUMN_NAME_IMAGE, product.getImageUrl());
            values.put(ProductEntity.COLUMN_NAME_NAME, product.getName());
            values.put(ProductEntity.COLUMN_NAME_DISCOUNT, product.getDiscount());
            values.put(ProductEntity.COLUMN_NAME_OLD_PRICE, Util.getFloatValAvoidingNullable(product.getOldPrice()));
            values.put(ProductEntity.COLUMN_NAME_PRICE, product.getPrice().floatValue());
            values.put(ProductEntity.COLUMN_NAME_RATE, product.getRate());
            values.put(ProductEntity.COLUMN_NAME_CATEGORY, product.getCategoryType() == null ? null : product.getCategoryType().name());
            values.put(ProductEntity.COLUMN_NAME_DESCRIPTION, product.getDescription());
            values.put(ProductEntity.COLUMN_NAME_UUID, UUID.randomUUID().toString());

            db.insertOrThrow(ProductEntity.TABLE_NAME, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add product to database");
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }


    public void addOrder(OrderItemVM order) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ProductEntity.Entry entry = getProductEntryByUUID(order.getProduct().getId());
            ContentValues values = new ContentValues();
            values.put(OrderEntity.COLUMN_NAME_PRODUCT_UUID, entry.id);
            values.put(OrderEntity.COLUMN_NAME_QTE, order.getQuantity());

            db.insertOrThrow(OrderEntity.TABLE_NAME, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add OrderItem to database");
            e.getStackTrace();
        } finally {
            db.endTransaction();
        }
    }


    public int updateOrder(OrderItemVM order) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OrderEntity.COLUMN_NAME_QTE, order.getQuantity());
        return db.update(OrderEntity.TABLE_NAME, values, OrderEntity.COLUMN_NAME_ID + " = ?",
                new String[]{String.valueOf(order.getId())});
    }

    public List<ProductVM> getAllProducts() {
        List<ProductVM> products = new ArrayList<>();
        String PRODUCTS_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        ProductEntity.TABLE_NAME);
        queryToSelectProducts(products, PRODUCTS_SELECT_QUERY);
        System.out.println(products.size());
        System.out.println(products);
        return products;
    }

    public List<ProductVM> searchByCategory(String category) {
        List<ProductVM> products = new ArrayList<>();
        String PRODUCTS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s='%s'",
                        ProductEntity.TABLE_NAME, ProductEntity.COLUMN_NAME_CATEGORY, category);
        queryToSelectProducts(products, PRODUCTS_SELECT_QUERY);
        return products;
    }

    private void queryToSelectProducts(List<ProductVM> products, String PRODUCTS_SELECT_QUERY) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(PRODUCTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ProductEntity.Entry newEntry = new ProductEntity.Entry();
                    newEntry.id = cursor.getInt(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_ID));
                    newEntry.uuid = cursor.getString(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_UUID));
                    newEntry.name = cursor.getString(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_NAME));
                    newEntry.image = cursor.getString(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_IMAGE));
                    newEntry.price = cursor.getFloat(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_PRICE));
                    newEntry.discount = cursor.getFloat(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_DISCOUNT));
                    newEntry.oldPprice = cursor.getFloat(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_OLD_PRICE));
                    newEntry.rate = cursor.getFloat(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_RATE));
                    newEntry.category = cursor.getString(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_CATEGORY));
                    newEntry.description = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntity.COLUMN_NAME_DESCRIPTION));
                    products.add(ProductEntity.entryToVm(newEntry));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get produts from database");
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    public boolean deleteOrder(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(OrderEntity.TABLE_NAME, OrderEntity.COLUMN_NAME_ID + "=" + id, null) > 0;
    }

    public boolean deleteAllOrders() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(OrderEntity.TABLE_NAME, null, null) > 0;
    }

    public List<OrderItemVM> getAllOrders() {
        List<OrderItemVM> orderItems = new ArrayList<>();
        String PRODUCTS_SELECT_QUERY =
                String.format("SELECT * FROM %s INNER JOIN %s ON %s.%s = %s.%s",
                        OrderEntity.TABLE_NAME,
                        ProductEntity.TABLE_NAME,
                        OrderEntity.TABLE_NAME, OrderEntity.COLUMN_NAME_PRODUCT_UUID,
                        ProductEntity.TABLE_NAME, ProductEntity.COLUMN_NAME_ID);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(PRODUCTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ProductEntity.Entry newEntry = new ProductEntity.Entry();
                    newEntry.id = cursor.getInt(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_ID));
                    newEntry.uuid = cursor.getString(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_UUID));
                    newEntry.name = cursor.getString(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_NAME));
                    newEntry.image = cursor.getString(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_IMAGE));
                    newEntry.price = cursor.getFloat(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_PRICE));
                    newEntry.oldPprice = cursor.getFloat(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_OLD_PRICE));
                    newEntry.discount = cursor.getFloat(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_DISCOUNT));
                    newEntry.rate = cursor.getFloat(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_RATE));
                    newEntry.description = cursor.getString(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_DESCRIPTION));
                    newEntry.category = cursor.getString(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_CATEGORY));
                    int id = cursor.getInt(cursor.getColumnIndex(OrderEntity.COLUMN_NAME_ID));
                    int qte = cursor.getInt(cursor.getColumnIndex(OrderEntity.COLUMN_NAME_QTE));
                    OrderItemVM order = new OrderItemVM(ProductEntity.entryToVm(newEntry), qte);
                    order.setId(id);
                    orderItems.add(order);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get orderItem from database");
            e.getStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return orderItems;
    }

    public ProductEntity.Entry getProductEntryByUUID(String uuid) {
        String PRODUCTS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = ?",
                        ProductEntity.TABLE_NAME, ProductEntity.COLUMN_NAME_UUID);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(PRODUCTS_SELECT_QUERY, new String[]{uuid});
        try {
            if (cursor.moveToFirst()) {
                ProductEntity.Entry newEntry = new ProductEntity.Entry();
                newEntry.id = cursor.getInt(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_ID));
                newEntry.uuid = cursor.getString(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_UUID));
                newEntry.name = cursor.getString(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_NAME));
                newEntry.image = cursor.getString(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_IMAGE));
                newEntry.price = cursor.getFloat(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_PRICE));
                newEntry.oldPprice = cursor.getFloat(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_OLD_PRICE));
                newEntry.discount = cursor.getFloat(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_DISCOUNT));
                newEntry.rate = cursor.getFloat(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_RATE));
                newEntry.description = cursor.getString(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_DESCRIPTION));
                newEntry.category = cursor.getString(cursor.getColumnIndex(ProductEntity.COLUMN_NAME_CATEGORY));
                return newEntry;
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return null;
    }

    private void initData() {
        if (getAllProducts().size() == 0) {
            List<ProductVM> vms = Arrays.asList(
                    new ProductVM(UUID.randomUUID().toString(), "Nike Air max 20 ABC", new BigDecimal("230.00"),
                            4.5F, "https://disuku.xyz/soko/5/paypal-test-integration/2bce6bb5904049589cb9a899ca07e2bd.png",
                            new BigDecimal("290.00"), 10F, CategoryType.SHOES, Lorem.getWords(30, 123)),
                    new ProductVM(UUID.randomUUID().toString(), "Nike Air max 20 Black DEF", new BigDecimal("210.00"),
                            3.1F, "https://disuku.xyz/soko/5/paypal-test-integration/dd1e3f613ba74488874e45caf01a1c27.png",
                            new BigDecimal("390.00"), 12F, CategoryType.SHOES, Lorem.getWords(30, 123)),
                    new ProductVM(UUID.randomUUID().toString(), "Men's Shoe - Nike Space Hippie 04", new BigDecimal("476.00"), 4.5F,
                            "https://disuku.xyz/soko/5/paypal-test-integration/572b6af84e83453bb5b75fe0f9703cfd.png",
                            null, null, CategoryType.SHOES, Lorem.getWords(30, 123)),
                    new ProductVM(UUID.randomUUID().toString(), "Men's Training Shoe - Nike Free Metcon 3", new BigDecimal("90.00"), 5F,
                            //"https://disuku.xyz/soko/5/paypal-test-integration/7ae8e29f7f7545d693beb700b7c42fe2.jpg",
                            "https://disuku.xyz/soko/5/paypal-test-integration/904c748b5df84e5eb4f3f495729e52d6.jpg",
                            null, null, CategoryType.SHOES, Lorem.getWords(30, 123)),
                    new ProductVM(UUID.randomUUID().toString(), "Nike Air max 20 LMN", new BigDecimal("129.70"), 4.5F,
                            "https://disuku.xyz/soko/5/paypal-test-integration/e609c3b8dea84d33af95c62d6d70dc12.png",
                            null, 45F, CategoryType.SHOES, Lorem.getWords(30, 123)),
                    new ProductVM(UUID.randomUUID().toString(), "Nike Air max 20 Black OPQ", new BigDecimal("216.00"), 2.1F,
                            "https://disuku.xyz/soko/5/paypal-test-integration/dd1e3f613ba74488874e45caf01a1c27.png",
                            null, null, CategoryType.CLOTHES, Lorem.getWords(30, 123)),
                    new ProductVM(UUID.randomUUID().toString(), "xxxxx xxxx xxxxx xxxx", new BigDecimal("216.00"), 2.1F,
                            "https://disuku.xyz/soko/5/paypal-test-integration/38c3242b219f45cc9f4437289136d4e6.png",
                            null, null, CategoryType.CLOTHES, Lorem.getWords(30, 123)),
                    new ProductVM(UUID.randomUUID().toString(), "Nike Air max 20 RST", new BigDecimal("98.00"), 1.5F,
                            "https://disuku.xyz/soko/5/paypal-test-integration/2bce6bb5904049589cb9a899ca07e2bd.png",
                            null, null, CategoryType.SHOES, Lorem.getWords(23, 234)),
                    new ProductVM(UUID.randomUUID().toString(), "Nike Air max 20 RST", new BigDecimal("98.00"), 1.5F,
                            "https://disuku.xyz/soko/5/paypal-test-integration/1b34ae0d5c9f4cc88acb3ca1cf69c1b3.png",
                            null, null, CategoryType.SHOES, Lorem.getWords(23, 234)),
                    new ProductVM(UUID.randomUUID().toString(), "Nike Air max 20 RST", new BigDecimal("98.00"), 1.5F,
                            "https://disuku.xyz/soko/5/paypal-test-integration/82402f6151344cf893001c8e48b62df0.png",
                            null, null, CategoryType.SHOES, Lorem.getWords(23, 234)),

                    // PANTS
                    /*new ProductVM(UUID.randomUUID().toString(),"Pants blue fashion clothes woman",new BigDecimal("28.99"),3.8F,
                            "https://disuku.xyz/soko/5/paypal-test-integration/4fc962543e844a76b3073b67adbd6b30.jpg",
                            null, null, CategoryType.PANTS, Lorem.getWords(30, 123)),*/
                    new ProductVM(UUID.randomUUID().toString(), "Cloth jeans pants textile material fashion", new BigDecimal("24.00"), 5F,
                            "https://disuku.xyz/soko/5/paypal-test-integration/fc8ac9541f704840a1fb9dc486dbeea3.jpg",
                            null, null, CategoryType.PANTS, Lorem.getWords(30, 123)),
                    new ProductVM(UUID.randomUUID().toString(), "Women's Fleece Pants - Jordan Flight", new BigDecimal("18.50"), 4.5F,
                            "https://disuku.xyz/soko/5/paypal-test-integration/408a0e17125d4b50a56fffce56a7e6df.png",
                            //"https://disuku.xyz/soko/5/paypal-test-integration/b2090f7da2cc4dd1adf2057650740426.png",
                            BigDecimal.valueOf(35), 25F, CategoryType.PANTS, Lorem.getWords(30, 123)),
                    new ProductVM(UUID.randomUUID().toString(), "Colorful cloth textil material object", new BigDecimal("18.50"), 4.5F,
                            "https://disuku.xyz/soko/5/paypal-test-integration/a8da8a71ac794e569775c57dfb1ec155.jpg",
                            BigDecimal.valueOf(35), 25F, CategoryType.PANTS, Lorem.getWords(30, 123)),

                    //BAGS
                    new ProductVM(UUID.randomUUID().toString(), "Aya Laptop Bag by Mamtak Bags", new BigDecimal("120.50"), 3.5F,
                            //"https://disuku.xyz/soko/5/paypal-test-integration/b2540e3d36ec4208a0173f5a3f910198.jpg",
                            "https://disuku.xyz/soko/5/paypal-test-integration/46dac464b815438e900ca230fcfde7bb.png",
                            BigDecimal.valueOf(340.5f), 35F, CategoryType.BAGS, Lorem.getWords(30, 123)),
                    new ProductVM(UUID.randomUUID().toString(), "Carrying bags black model - Chromy XDMM", new BigDecimal("170.50"), 3.5F,
                            "https://disuku.xyz/soko/5/paypal-test-integration/99343959231f4fde8765926c8c420b11.png",
                            BigDecimal.valueOf(240.5f), 35F, CategoryType.BAGS, Lorem.getWords(30, 123)),
                    /*new ProductVM(UUID.randomUUID().toString(),"Ladies orange bags",new BigDecimal("70.50"),3.5F,
                            "https://www.publicdomainpictures.net/pictures/340000/velka/ladies-orange-bags.jpg",
                            BigDecimal.valueOf(140.5f), 5F, CategoryType.BAGS, Lorem.getWords(30, 123)),
                    new ProductVM(UUID.randomUUID().toString(),"Ladies orange bags",new BigDecimal("70.50"),3.5F,
                            "https://www.publicdomainpictures.net/pictures/340000/velka/ladies-orange-bags.jpg",
                            BigDecimal.valueOf(140.5f), 5F, CategoryType.BAGS, Lorem.getWords(30, 123)),*/

                    // HATS
                    new ProductVM(UUID.randomUUID().toString(), "Monticristi Straw Hat Optimo", new BigDecimal("470.50"), 3.5F,
                            "https://disuku.xyz/soko/5/paypal-test-integration/572b6af84e83453bb5b75fe0f9703cfd.png",
                            BigDecimal.valueOf(700f), 15F, CategoryType.HATS, Lorem.getWords(30, 123)),
                    new ProductVM(UUID.randomUUID().toString(), "Cap, man, hat, mannequin", new BigDecimal("470.50"), 3.5F,
                            // "https://i0.hippopx.com/photos/809/506/484/cap-man-hat-mannequin-0103d48b8e86d660f622f493b5695b6e.jpg",
                            "https://disuku.xyz/soko/5/paypal-test-integration/135fe329f67a47d1a4bcd8b3dcc39fc0.png",
                            BigDecimal.valueOf(700f), 15F, CategoryType.HATS, Lorem.getWords(30, 123))
            );

            Collections.shuffle(vms);
            for (ProductVM vm : vms) {
                addProduct(vm);
            }
        }
    }
}
