package com.example.storemanagement.entity;

import com.example.storemanagement.tool.UserDao;

import java.io.Serializable;
import java.util.Map;

public class StoreBuilder implements Serializable {
    private Map<String,Shelf> shelves;
    UserDao userDao =new UserDao();

    public Map<String,Shelf> buildShelves(){
        shelves =  userDao.getWare().getShelves();
        return shelves;
    }
}
