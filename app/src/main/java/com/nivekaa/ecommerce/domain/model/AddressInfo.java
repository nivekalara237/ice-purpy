package com.nivekaa.ecommerce.domain.model;

public class AddressInfo {
    private String id;
    private String country;
    private String address;
    private String state;
    private String city;
    private String zipcode;

    public AddressInfo() {
    }

    public AddressInfo(String country, String address, String state, String city, String zipcode) {
        this.country = country;
        this.address = address;
        this.state = state;
        this.city = city;
        this.zipcode = zipcode;
    }

    public AddressInfo(String id, String country, String address, String state, String city, String zipcode) {
        this(country, address, state, city, zipcode);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "AddressInfo{" +
                "id='" + id + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
