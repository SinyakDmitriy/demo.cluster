package com.demo.cluster.spring.annotations;

import com.demo.cluster.model.mysql.Role;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRole {
    Role value();
}
