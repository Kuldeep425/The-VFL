package com.example.thevfl.Model;

public class CategoryShowingData {
    private String category;
    int ImageUrl;

    public CategoryShowingData(String category, int imageUrl) {
        this.category = category;
        ImageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(int imageUrl) {
        ImageUrl = imageUrl;
    }
}
