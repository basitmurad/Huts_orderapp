package com.afaq.huts.model;

public class BreakfastClass {

    private String name , price ;
    private int imageUri ;

    public BreakfastClass(String name, String price, int imageUri) {
        this.name = name;
        this.price = price;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImageUri() {
        return imageUri;
    }

    public void setImageUri(int imageUri) {
        this.imageUri = imageUri;
    }
}
