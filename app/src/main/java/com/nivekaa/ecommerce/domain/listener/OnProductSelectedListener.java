package com.nivekaa.ecommerce.domain.listener;

import com.nivekaa.ecommerce.domain.model.ProductVM;

public interface OnProductSelectedListener {
    void productSelected(ProductVM product);
}
