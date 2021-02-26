package com.example.lizmee_end_user;

public class CategoryRecyclerModel {

    String categoryImage;
    String categoryText;

    public CategoryRecyclerModel(){}


    public CategoryRecyclerModel(String categoryImage, String categoryText) {
        this.categoryImage = categoryImage;
        this.categoryText = categoryText;
    }


    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }
}
