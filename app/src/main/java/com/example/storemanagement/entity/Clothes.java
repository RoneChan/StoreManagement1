package com.example.storemanagement.entity;

import java.io.Serializable;

public class Clothes implements Serializable,Product{
    private String id;
    private int number;

    public Clothes(String id, int number) {
        this.id = id;
        this.number = number;
    }

    @Override
   public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
