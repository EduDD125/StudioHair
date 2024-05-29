package com.example.sistemacabeleleiro.Domain.Entities.Service;

public class Service {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String subCategory;
    private Double discount;

    public Service(String name, String description, Double price, String category, String subCategory, Double discount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.subCategory = subCategory;
        this.discount = discount;
    }

    public Service(Integer id, String name, String description, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = null;
        this.subCategory = null;
        this.discount = 0.0;
    }

    public Service(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = null;
        this.subCategory = null;
        this.discount = 0.0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
