package com.afaq.huts.model;

import java.util.ArrayList;

public class CartDetails {
    private ArrayList<String> itemDataList;
    private int totalCartPrice;

    public CartDetails(ArrayList<String> itemDataList, int totalCartPrice) {
        this.itemDataList = itemDataList;
        this.totalCartPrice = totalCartPrice;
    }

    public ArrayList<String> getItemDataList() {
        return itemDataList;
    }

    public int getTotalCartPrice() {
        return totalCartPrice;
    }
}

