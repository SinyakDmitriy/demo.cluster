package com.demo.cluster.spring.model;

import lombok.Data;

@Data
public class User {

    private String name;

    public User(String name){
        this.name = name;
    }

}
