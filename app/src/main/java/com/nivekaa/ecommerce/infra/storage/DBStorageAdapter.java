package com.nivekaa.ecommerce.infra.storage;

import android.content.Context;

import com.nivekaa.ecommerce.domain.model.CategoryType;
import com.nivekaa.ecommerce.domain.model.LabelVM;
import com.nivekaa.ecommerce.domain.model.OrderItemVM;
import com.nivekaa.ecommerce.domain.model.ProductVM;
import com.nivekaa.ecommerce.domain.spi.DBStoragePort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBStorageAdapter implements DBStoragePort {
    private final DBHelper dbHelper;

    public DBStorageAdapter(Context context) {
        this.dbHelper = DBHelper.getInstance(context);
    }

    @Override
    public void init(Context context) {
        //this.dbHelper = DBHelper.getInstance(context);
    }

    @Override
    public List<ProductVM> getPants() {
        if (dbHelper != null)
            return dbHelper.searchByCategory(CategoryType.PANTS.name());
        return new ArrayList<>();
    }

    @Override
    public List<ProductVM> getShoes() {
        if (dbHelper != null)
            return dbHelper.searchByCategory(CategoryType.SHOES.name());
        return new ArrayList<>();
    }

    @Override
    public List<ProductVM> getTshirts() {
        if (dbHelper != null)
            return dbHelper.searchByCategory(CategoryType.TSHIRTS.name());
        return new ArrayList<>();
    }

    @Override
    public List<ProductVM> getHats() {
        if (dbHelper != null)
            return dbHelper.searchByCategory(CategoryType.HATS.name());
        return new ArrayList<>();
    }

    @Override
    public List<ProductVM> getBags() {
        if (dbHelper != null)
            return dbHelper.searchByCategory(CategoryType.BAGS.name());
        return new ArrayList<>();
    }

    @Override
    public List<OrderItemVM> getOrders() {
        if (dbHelper != null)
            return dbHelper.getAllOrders();
        return new ArrayList<>();
    }

    @Override
    public void addOrder(OrderItemVM order) {
        dbHelper.addOrder(order);
    }

    @Override
    public void addProduct(ProductVM productVM) {
        dbHelper.addProduct(productVM);
    }

    @Override
    public List<LabelVM> getLabels() {
        return Arrays.asList(
                new LabelVM("All", CategoryType.ALL),
                new LabelVM("Shoes", CategoryType.SHOES),
                new LabelVM("Pants", CategoryType.PANTS),
                new LabelVM("T-shirts", CategoryType.TSHIRTS),
                new LabelVM("Hats", CategoryType.HATS),
                new LabelVM("Bags", CategoryType.BAGS),
                new LabelVM("Clothes", CategoryType.CLOTHES),
                new LabelVM("Furnitures", CategoryType.FURNITURES)
        );
    }

    @Override
    public List<ProductVM> searchAny(CategoryType category) {
        if (dbHelper != null) {
            if (category == CategoryType.ALL)
                return dbHelper.getAllProducts();
            else return dbHelper.searchByCategory(category.name());
        }
        return new ArrayList<>();
    }
}
