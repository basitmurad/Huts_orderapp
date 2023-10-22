package com.afaq.huts.model;

public class  DishDetail {
    private String name;
    private String HutName;
    private int price;
    private int quantity;
    private byte[] imageByteArray;
    private int newPrice , newQuantity;



    public DishDetail(String name, int price, int quantity, byte[] imageByteArray) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageByteArray = imageByteArray;
    }
    public DishDetail(String hutName ,String name, int price, int quantity, byte[] imageByteArray) {
        this.HutName = hutName;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageByteArray = imageByteArray;
    }

    public String getHutName() {
        return HutName;
    }

    public void setHutName(String hutName) {
        HutName = hutName;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public byte[] getImageByteArray() {
        return imageByteArray;
    }

    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }

    public int getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(int newPrice) {
        this.newPrice = newPrice;
    }

    public int getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(int newQuantity) {
        this.newQuantity = newQuantity;
    }
}
