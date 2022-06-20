package com.example.thevfl.Model;

public class MyProductData {
    private String productName,categoryName,price,description,imageUrl;

    public MyProductData(String productName, String categoryName, String price, String description, String imageUrl) {
        this.productName = productName;
        this.categoryName = categoryName;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
