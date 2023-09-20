package com.afaq.huts;

import android.widget.ImageView;

public class DashboardClass {

    String dishName , dishDiscription;
        int imageUri;

    public DashboardClass(String dishName, String dishDiscription, int imageUri) {
        this.dishName = dishName;
        this.dishDiscription = dishDiscription;
        this.imageUri = imageUri;
    }


    public DashboardClass(String dishName, String dishDiscription) {
        this.dishName = dishName;
        this.dishDiscription = dishDiscription;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishDiscription() {
        return dishDiscription;
    }

    public void setDishDiscription(String dishDiscription) {
        this.dishDiscription = dishDiscription;
    }

    public int getImageUri() {
        return imageUri;
    }

    public void setImageUri(int imageUri) {
        this.imageUri = imageUri;
    }
}
