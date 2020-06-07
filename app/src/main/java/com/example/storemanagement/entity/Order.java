package com.example.storemanagement.entity;

import java.util.ArrayList;
import java.util.Map;

public interface Order {
    public ArrayList<Product> getOderList();
    public void setOderList(ArrayList<Product> oderList);
    public String getId();
    public void setId(String id);

}
