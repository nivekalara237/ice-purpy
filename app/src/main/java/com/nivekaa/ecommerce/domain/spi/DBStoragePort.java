package com.nivekaa.ecommerce.domain.spi;

import android.content.Context;

import com.nivekaa.ecommerce.domain.model.CategoryType;
import com.nivekaa.ecommerce.domain.model.LabelVM;
import com.nivekaa.ecommerce.domain.model.OrderItemVM;
import com.nivekaa.ecommerce.domain.model.ProductVM;

import java.util.List;

public interface DBStoragePort {
    void init(Context context);
    List<ProductVM> getPants();
    List<ProductVM> getShoes();
    List<ProductVM> getTshirts();
    List<ProductVM> getHats();
    List<ProductVM> getBags();
    List<OrderItemVM> getOrders();
    void addOrder(OrderItemVM order);
    void addProduct(ProductVM productVM);
    List<LabelVM> getLabels();
    List<ProductVM> searchAny(CategoryType category);

}
