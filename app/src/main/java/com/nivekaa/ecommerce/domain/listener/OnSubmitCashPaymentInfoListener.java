package com.nivekaa.ecommerce.domain.listener;

import com.nivekaa.ecommerce.domain.model.CashOrCardPaymentInfo;

public interface OnSubmitCashPaymentInfoListener {
    void cashSubmit(CashOrCardPaymentInfo form);
}
