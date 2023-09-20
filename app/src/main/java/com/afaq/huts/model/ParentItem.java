package com.afaq.huts.model;

public class ParentItem {
    private String orderId , hutName , totalPrice;

    public ParentItem(String orderId, String hutName, String totalPrice) {
        this.orderId = orderId;
        this.hutName = hutName;
        this.totalPrice = totalPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getHutName() {
        return hutName;
    }

    public void setHutName(String hutName) {
        this.hutName = hutName;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
