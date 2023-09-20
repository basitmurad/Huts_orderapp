package com.afaq.huts.model;

import android.graphics.Bitmap;

public class CartItem {
    private String name;
    private int price;
    private Bitmap imageBitmap;
    private int quantity;

    public CartItem(String name, int price, Bitmap imageBitmap, int quantity) {
        this.name = name;
        this.price = price;
        this.imageBitmap = imageBitmap;
        this.quantity = quantity;
    }

    public CartItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
