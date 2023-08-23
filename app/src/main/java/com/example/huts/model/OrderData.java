package com.example.huts.model;

import java.util.ArrayList;

public class OrderData {


    private String hutName , userId , pushId;
    private int totalPrice;
    private ArrayList<OrderDetails> orderDetailsList;

    private boolean isActive ;
    public OrderData() {
        // Default constructor required for Firebase
    }

    public OrderData(String hutName, String userId, String pushId, int totalPrice, ArrayList<OrderDetails> orderDetailsList, boolean isActive) {
        this.hutName = hutName;
        this.userId = userId;
        this.pushId = pushId;
        this.totalPrice = totalPrice;
        this.orderDetailsList = orderDetailsList;
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public OrderData(String hutName, String userId, String pushId, int totalPrice, ArrayList<OrderDetails> orderDetailsList) {
        this.hutName = hutName;
        this.userId = userId;
        this.pushId = pushId;
        this.totalPrice = totalPrice;
        this.orderDetailsList = orderDetailsList;
    }

    public String getHutName() {
        return hutName;
    }

    public void setHutName(String hutName) {
        this.hutName = hutName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<OrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(ArrayList<OrderDetails> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }
}


