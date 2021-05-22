package com.nivekaa.ecommerce.domain.model;

import com.craftman.cardform.Card;

public class CashOrCardPaymentInfo {
    private String fullname;
    private String email;
    private String phone;
    private AddressInfo addressInfo;
    private Card cardInfo;

    public CashOrCardPaymentInfo() {
    }

    public CashOrCardPaymentInfo(String fullname, String email, String phone) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
    }

    public CashOrCardPaymentInfo(String fullname, String email, String phone, AddressInfo addressInfo) {
        this(fullname, email, phone);
        this.addressInfo = addressInfo;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    public Card getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(Card cardInfo) {
        this.cardInfo = cardInfo;
    }

    @Override
    public String toString() {
        return "CashPaymentInfo{" +
                "fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", addressInfo=" + addressInfo +
                '}';
    }
}
