package com.example.sistemacabeleleiro.Domain.Entities.Service;


public class Service {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String subCategory;
    private Double discount;
    private ServiceStatus status;

    public Service() {
        this.status = ServiceStatus.ACTIVE;
    }

    public Service(String name, String description, Double price, String category, String subCategory,
                   Double discount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.subCategory = subCategory;
        this.discount = discount;
        this.status = ServiceStatus.ACTIVE;
    }

    public Service(Integer id, String name, String description, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = null;
        this.subCategory = null;
        this.discount = 0.0;
        this.status = ServiceStatus.ACTIVE;
    }

    public Service(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = null;
        this.subCategory = null;
        this.discount = 0.0;
        this.status = ServiceStatus.ACTIVE;
    }

    public Service(String name, String description, Double price, ServiceStatus status) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = null;
        this.subCategory = null;
        this.discount = 0.0;
        this.status = status;
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
    public Double getValueOfService() {
        return price - discount;
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

    public ServiceStatus getStatus() {
        return status;
    }
    public void activateStatus() {this.status = ServiceStatus.ACTIVE;}
    public void inactivateStatus() {this.status = ServiceStatus.INACTIVE;}

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", discount=" + discount +
                ", status=" + status +
                '}';
    }
}
