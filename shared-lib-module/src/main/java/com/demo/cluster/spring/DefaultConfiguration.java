package com.demo.cluster.spring;

import com.demo.cluster.spring.annotations.InterfaceScaner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

@Configuration
@InterfaceScaner("com.demo.cluster")
public class DefaultConfiguration {
    @Bean
    public ParameterNameDiscoverer parameterNameDiscoverer() {
        return new DefaultParameterNameDiscoverer();
    }
}
