package com.example.storemanagement.entity;

public class Info {
    public String id;
    public String position;
    public int resNumber;
    public int needNumber;

    public Info(String id, String position, int resNumber,int needNumber) {
        this.id = id;
        this.position = position;
        this.resNumber = resNumber;
        this.needNumber=needNumber;
    }
}
