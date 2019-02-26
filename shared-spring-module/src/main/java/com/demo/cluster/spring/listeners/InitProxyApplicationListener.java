package com.demo.cluster.spring.listeners;

import com.demo.cluster.spring.annotations.InitProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class InitProxyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired private ApplicationContext context;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String[] beanNames =  context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            Method[] methods = bean.getClass().getMethods();
            for (Method method : methods) {
                if(method.isAnnotationPresent(InitProxy.class)) {
                    try {
                        method.invoke(bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
    }
}
