package com.example.storemanagement.entity;

public interface Factory {
    public Product produce(String id, int number);
}
