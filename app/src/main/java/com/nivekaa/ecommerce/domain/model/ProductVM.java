package com.nivekaa.ecommerce.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductVM implements Serializable {
    private CategoryType categoryType;
    private String id;
    private String name;
    private BigDecimal price;
    private Integer inStock;
    private Float rate;
    private String imageUrl;
    private String bgColor;
    private String description;
    private BigDecimal oldPrice;
    private Float discount;

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public ProductVM() {
    }

    public ProductVM(String id, String name, BigDecimal price, Float rate, String imageUrl) {
        this();
        this.id = id;
        this.name = name;
        this.price = price;
        this.rate = rate;
        this.imageUrl = imageUrl;
    }

    public ProductVM(String id, String name, BigDecimal price, Float rate, String imageUrl, BigDecimal oldPrice, Float discount) {
        this(id, name, price, rate, imageUrl);
        this.oldPrice = oldPrice;
        this.discount = discount;
    }

    public ProductVM(String id, String name, BigDecimal price, Float rate, String imageUrl, BigDecimal oldPrice, Float discount, CategoryType ctype) {
        this(id, name, price, rate, imageUrl, oldPrice, discount);
        this.categoryType = ctype;
    }

    public ProductVM(String id, String name, BigDecimal price, Float rate, String imageUrl, BigDecimal oldPrice, Float discount, CategoryType ctype, String description) {
        this(id, name, price, rate, imageUrl, oldPrice, discount, ctype);
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProductVM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", inStock=" + inStock +
                ", rate=" + rate +
                ", oldPrice=" + oldPrice +
                ", discount=" + discount +
                ", imageUrl='" + imageUrl + '\'' +
                ", bgColor='" + bgColor + '\'' +
                ", catrgoryType='" + categoryType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
