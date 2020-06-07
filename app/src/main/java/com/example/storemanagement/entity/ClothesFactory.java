package com.example.storemanagement.entity;

public class ClothesFactory implements Factory{

    @Override
    public Product produce(String id, int number) {
        return new Clothes(id,number) ;
    }
}
