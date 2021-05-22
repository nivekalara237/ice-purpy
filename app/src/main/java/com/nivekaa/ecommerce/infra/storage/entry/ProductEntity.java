package com.nivekaa.ecommerce.infra.storage.entry;

import com.nivekaa.ecommerce.domain.model.CategoryType;
import com.nivekaa.ecommerce.domain.model.ProductVM;

import java.math.BigDecimal;

public class ProductEntity {
    public static final String TABLE_NAME = "product_entry";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_UUID = "_uuid";
    public static final String COLUMN_NAME_ID = "product_id";
    public static final String COLUMN_NAME_PRICE = "price";
    public static final String COLUMN_NAME_DISCOUNT = "_discount";
    public static final String COLUMN_NAME_OLD_PRICE = "old_price";
    public static final String COLUMN_NAME_RATE = "rate";
    public static final String COLUMN_NAME_IMAGE = "image";
    public static final String COLUMN_NAME_CATEGORY = "categorie";
    public static final String COLUMN_NAME_DESCRIPTION = "description";

    public static class Entry {
        public String uuid;
        public Float price;
        public Float oldPprice;
        public String image;
        public String name;
        public Integer id;
        public Float rate;
        public Float discount;
        public String category;
        public String description;

        public Entry() {
        }

        public Entry(String uuid, Float price, String image, String name, Float rate) {
            this.uuid = uuid;
            this.price = price;
            this.image = image;
            this.name = name;
            this.rate = rate;
        }

        public Entry(String uuid, Float price, String image, String name, Float rate, Float oldPrice, Float discount) {
            this(uuid, price, image, name, rate);
            this.discount = discount;
            this.oldPprice = oldPrice;
        }

        public Entry(String uuid, Float price, String image, String name, Float rate, Float oldPrice, Float discount, String category, String desc) {
            this(uuid, price, image, name, rate, oldPrice, discount);
            this.category = category;
            this.description = desc;
        }
    }

    public static Entry vmToEntry(ProductVM vm){
        return new Entry(vm.getId(), vm.getPrice().floatValue(), vm.getImageUrl(), vm.getName(), vm.getRate());
    }

    public static ProductVM entryToVm(Entry entry) {
        return new ProductVM(
                entry.uuid,
                entry.name,
                BigDecimal.valueOf(entry.price),
                entry.rate,
                entry.image,
                BigDecimal.valueOf(entry.oldPprice),
                entry.discount,
                entry.category != null ? CategoryType.valueOf(entry.category.toUpperCase()) : CategoryType.UNCATEGORIZED,
                entry.description
        );
    }
}
