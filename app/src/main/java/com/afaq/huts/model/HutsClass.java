package com.afaq.huts.model;

public class HutsClass {
    private String hutsName , hutTiming ;
    private int imageUri ;

    public HutsClass(String hutsName, String hutTiming, int imageUri) {
        this.hutsName = hutsName;
        this.hutTiming = hutTiming;
        this.imageUri = imageUri;
    }

    public String getHutsName() {
        return hutsName;
    }

    public void setHutsName(String hutsName) {
        this.hutsName = hutsName;
    }

    public String getHutTiming() {
        return hutTiming;
    }

    public void setHutTiming(String hutTiming) {
        this.hutTiming = hutTiming;
    }

    public int getImageUri() {
        return imageUri;
    }

    public void setImageUri(int imageUri) {
        this.imageUri = imageUri;
    }
}
