package com.example.storemanagement.entity;

public class Inventory {
    private String oriId;
    private String curId;
    private int oriNumber;
    private int curNumber;

    public Inventory(String oriId, String curId, int oriNumber, int curNumber) {
        this.oriId = oriId;
        this.curId = curId;
        this.oriNumber = oriNumber;
        this.curNumber = curNumber;
    }

    public String getOriId() {
        return oriId;
    }

    public String getCurId() {
        return curId;
    }

    public int getOriNumber() {
        return oriNumber;
    }

    public int getCurNumber() {
        return curNumber;
    }
}
