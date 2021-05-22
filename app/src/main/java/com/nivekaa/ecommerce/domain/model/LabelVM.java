package com.nivekaa.ecommerce.domain.model;


public class LabelVM {
    private String title;
    private String color;
    private CategoryType category;

    public LabelVM(String title) {
        this.title = title;
    }

    public LabelVM(String title, CategoryType category) {
        this(title);
        this.category = category;
    }

    public LabelVM(String title, String color) {
        this.title = title;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "LabelVM{" +
                "title='" + title + '\'' +
                ", color='" + color + '\'' +
                ", category=" + category +
                '}';
    }
}
