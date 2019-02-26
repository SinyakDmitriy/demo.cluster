package com.demo.cluster.spring;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:application.properties", "classpath:${properties}"})
public class ApplicationConfiguration {

    @Value("${flyway.migration}") private String migration;
    @Value("${flyway.placeholder.prefix}") private String placeholderPrefix;
    @Value("${flyway.login}") private String login;
    @Value("${flyway.password}") private String password;
    @Value("${flyway.url}") private String url;

    @Autowired private Vertx vertx;

    @Bean
    public Flyway flyway(){
//        Flyway flyway = Flyway.configure()
//                .locations("classpath:migration")
//                .placeholderPrefix("V")
//                .dataSource("jdbc:mysql://localhost:3306/demo_cluster", login, password)
//                .load();
//        flyway.migrate();
//        return flyway;
        return null;
    }

    @Bean
    public EventBus eventBus(){
        return vertx.eventBus();
    }
}
