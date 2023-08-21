package com.example.huts.model;

import java.util.ArrayList;

public class OrderData {


    private String hutName;
    private int totalPrice;
    private ArrayList<OrderDetails> orderDetailsList;

    public OrderData() {
        // Default constructor required for Firebase
    }

    public OrderData(String hutName, int totalPrice, ArrayList<OrderDetails> orderDetailsList) {
        this.hutName = hutName;
        this.totalPrice = totalPrice;
        this.orderDetailsList = orderDetailsList;
    }

    public String getHutName() {
        return hutName;
    }

    public void setHutName(String hutName) {
        this.hutName = hutName;
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
//
}


