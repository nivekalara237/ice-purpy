package com.nivekaa.ecommerce.domain.listener;

import com.nivekaa.ecommerce.domain.model.PaymentMode;

public interface OnPaymentOptionListner {
    void paymentOptionSelected(PaymentMode paymentMode);
}
