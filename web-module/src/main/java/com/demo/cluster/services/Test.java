package com.demo.cluster.services;

import com.demo.cluster.spring.annotations.InitProxy;
import com.demo.cluster.spring.annotations.InjectService;
import org.springframework.stereotype.Component;

@Component
public class Test {
    @InjectService private UserService userService;

    @InitProxy
    public void init(){
        System.out.println(userService);
        System.out.println("+++++++++++++++++++++++");
    }
}
