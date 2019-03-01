package com.demo.cluster.spring.model;

import lombok.Getter;

@Getter
public class Argument {
    private String name;
    private Class type;
    private Object value;

    public Argument(String name, Class type, Object value) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Argument{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", value=" + value +
                '}';
    }
}
