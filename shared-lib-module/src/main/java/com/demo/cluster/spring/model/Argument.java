package com.demo.cluster.spring.model;

import lombok.Getter;

@Getter
public class Argument {
    private String name;
    private Class type;

    public Argument(String name, Class type) {
        this.name = name;
        this.type = type;
    }
}
