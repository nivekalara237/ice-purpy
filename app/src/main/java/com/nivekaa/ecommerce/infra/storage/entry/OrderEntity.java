package com.nivekaa.ecommerce.infra.storage.entry;

import com.nivekaa.ecommerce.domain.model.OrderItemVM;

public class OrderEntity {
    public static final String TABLE_NAME = "order_entry";
    public static final String COLUMN_NAME_PRODUCT_UUID = "product_fk_id";
    public static final String COLUMN_NAME_ID = "order_id";
    public static final String COLUMN_NAME_QTE = "quantity";

    public static class Entry {
        public Integer productUuid;
        public Integer qte;
        public Integer id;

        public Entry(Integer productUuid, Integer qte) {
            this.productUuid = productUuid;
            this.qte = qte;
        }
    }

    public static OrderEntity.Entry vmToEntry(OrderItemVM vm){
        //return new Entry(vm.getProduct().getId(), vm.getQuantity());
        return null;
    }
}
