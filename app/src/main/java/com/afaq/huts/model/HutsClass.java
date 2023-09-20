package com.afaq.huts.model;

public class HutsClass {
    private String hutsName ;
    private int imageUri ;

    public HutsClass(String hutsName, int imageUri) {
        this.hutsName = hutsName;
        this.imageUri = imageUri;
    }

    public String getHutsName() {
        return hutsName;
    }

    public void setHutsName(String hutsName) {
        this.hutsName = hutsName;
    }

    public int getImageUri() {
        return imageUri;
    }

    public void setImageUri(int imageUri) {
        this.imageUri = imageUri;
    }
}
