package com.example.huts.model;

import java.util.ArrayList;

public class ParentItem {
    private String hutName , userId , pushId ,orderId;
    private int totalPrice;
    private ArrayList<ChildItem> childItemArrayList;


    private boolean isActive ;


    public ParentItem(String hutName, String userId, String pushId, String orderId, int totalPrice, ArrayList<ChildItem> childItemArrayList, boolean isActive) {
        this.hutName = hutName;
        this.userId = userId;
        this.pushId = pushId;
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.childItemArrayList = childItemArrayList;
        this.isActive = isActive;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<ChildItem> getChildItemArrayList() {
        return childItemArrayList;
    }

    public void setChildItemArrayList(ArrayList<ChildItem> childItemArrayList) {
        this.childItemArrayList = childItemArrayList;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
