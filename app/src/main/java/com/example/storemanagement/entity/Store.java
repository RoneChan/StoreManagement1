package com.example.storemanagement.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Store implements Serializable {
    //private ArrayList<Shelf> shelves;
    private Map<String,Shelf> shelves;
    StoreBuilder storeBuilder ;

    public Store() {
        shelves = new HashMap<>();
    }

    public Store(HashMap<String,Shelf> shelves) {
        this.shelves = shelves;
    }

    public Map<String, Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(Map<String, Shelf> shelves) {
        this.shelves = shelves;
    }

    public void addShelf(Shelf shelf){
        shelves.put(shelf.getName(),shelf);
    }

    public void buildShelves(){
        storeBuilder=new StoreBuilder();
        shelves = storeBuilder.buildShelves();
        return;
    }




    public Clothes getClothesInfo(char shelfNumber,int pliesNumber){
        return shelves.get(shelfNumber-'A'-1).getClothesInfo(pliesNumber);
    }

    //根据Id查询剩余库存

    public int findRestNumber(String id){
        int shelfSize = shelves.size();
        for(int i=0;i<shelfSize;i++){
            Shelf shelf = shelves.get(i);
            int pliesNumber = shelf.FindRestNumber(id);//若pliesNumber=-1则该货架上没有该服装。
            if(pliesNumber != -1){
                return pliesNumber;
            }
        }
        return 0;
    }
}
