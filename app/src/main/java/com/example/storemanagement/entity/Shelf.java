package com.example.storemanagement.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Shelf implements Serializable {
    private String name;
    private int number;
    private Map<Integer,Clothes> piles;

    public Shelf(int number,String name) {
        this.number = number;
        this.name=name;
        this.piles = new HashMap<Integer, Clothes>();
    }

    public Shelf(String name) {
        this.number = 12;
        this.name=name;
        this.piles = new HashMap<>();
    }

    public Map<Integer, Clothes> getPiles() {
        return piles;
    }

    public void setPiles(Map<Integer, Clothes> piles) {
        this.piles = piles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    //查看该书架某层的服装
    public Clothes getClothesInfo(int number){
        return piles.get(number-1);
    }

    public int FindRestNumber(String id){
        int pliesSize=piles.size();
        for(int i=0;i<pliesSize;i++){
            if(piles.get(i).getId().equals(id)){
                return piles.get(i).getNumber();
            }
        }
        return -1;
    }
}
