package com.example.lizmee_end_user;

public  class ProductListModel {

    String productImage;
    String productName;
    String productCode;
    String productPrice;

    public ProductListModel(){}


    public ProductListModel(String productImage, String productName, String productCode, String productPrice) {
        this.productImage = productImage;
        this.productName = productName;
        this.productCode = productCode;
        this.productPrice = productPrice;
    }


    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
