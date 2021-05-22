package com.nivekaa.ecommerce.domain.listener;

import com.nivekaa.ecommerce.domain.model.OrderItemVM;

public interface OnXxCreaseQteItemListener {
    void increase(OrderItemVM order);
    void decrease(OrderItemVM order);
    void remove(OrderItemVM order);
    void allItemsHaveRemoved();
}
