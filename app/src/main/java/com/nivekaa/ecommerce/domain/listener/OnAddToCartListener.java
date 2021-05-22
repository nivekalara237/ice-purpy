package com.nivekaa.ecommerce.domain.listener;

import com.nivekaa.ecommerce.domain.model.ProductVM;

public interface OnAddToCartListener {
    void addProduct(ProductVM product);
}
