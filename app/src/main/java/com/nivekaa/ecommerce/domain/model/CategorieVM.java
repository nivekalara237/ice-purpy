package com.nivekaa.ecommerce.domain.model;

public class CategorieVM {
    private String name;
    private Integer id;
    private String description;
    private Integer discount;

    public CategorieVM(String name) {
        this.name = name;
    }

    public CategorieVM(String name, Integer _id) {
        this(name);
        this.id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

}
