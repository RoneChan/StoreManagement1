package com.example.storemanagement.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class ClothesOrder implements Order, Serializable {
    private ArrayList<Product> oderList;  //一个订单
    private String id="";

    public ClothesOrder(String id, ArrayList<Product> oderList) {
        this.oderList = oderList;
        this.id=id;
    }

    @Override
    public ArrayList<Product> getOderList() {
        return oderList;
    }

    @Override
    public void setOderList(ArrayList<Product> oderList) {
        this.oderList = oderList;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id=id;
    }
}
