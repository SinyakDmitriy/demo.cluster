package com.demo.cluster.model.mysql;

import lombok.Data;

@Data
public class User {

    private String name;
    private Role role;

    public User(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
