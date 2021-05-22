package com.nivekaa.ecommerce.domain.model;

public class OrderItemVM {
    private ProductVM product;
    private Integer quantity = 1;
    private Integer id;

    public OrderItemVM(ProductVM product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductVM getProduct() {
        return product;
    }

    public void setProduct(ProductVM product) {
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrderItemVM{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", id=" + id +
                '}';
    }
}
