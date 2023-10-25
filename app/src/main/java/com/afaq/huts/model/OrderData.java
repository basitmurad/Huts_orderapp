package com.afaq.huts.model;

import java.util.ArrayList;

public class OrderData {


    private String hutName , userId , pushId ,orderId ,address;
    private long timestamp;
    private int totalPrice;
    private ArrayList<OrderDetails> orderDetailsList;


    private boolean isActive ;


    public OrderData() {
    }

    public OrderData(String hutName, String userId, String pushId, String orderId, String address, int totalPrice, ArrayList<OrderDetails> orderDetailsList, boolean isActive) {
        this.hutName = hutName;
        this.userId = userId;
        this.pushId = pushId;
        this.orderId = orderId;
        this.address = address;
        this.totalPrice = totalPrice;
        this.orderDetailsList = orderDetailsList;
        this.isActive = isActive;
    }

    public OrderData(String hutName, String userId, String pushId, String orderId, String address, int totalPrice, ArrayList<OrderDetails> orderDetailsList, boolean isActive, long timestamp) {
        this.hutName = hutName;
        this.userId = userId;
        this.pushId = pushId;
        this.orderId = orderId;
        this.address = address;
        this.totalPrice = totalPrice;
        this.orderDetailsList = orderDetailsList;
        this.isActive = isActive;
        this.timestamp = timestamp; // Set the timestamp
    }
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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



    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}


