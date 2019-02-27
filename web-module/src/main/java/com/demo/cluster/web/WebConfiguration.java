package com.demo.cluster.web;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Autowired private Vertx vertx;

    @Bean
    public Router router(Vertx vertx){
        return Router.router(vertx);
    }

    @Bean
    public EventBus eventBus(){
        return vertx.eventBus();
    }

}
