package com.demo.cluster.spring.listeners;

import com.demo.cluster.spring.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Component
public class ApiAnnotationApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired private ConfigurableListableBeanFactory factory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context =  event.getApplicationContext();
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = factory.getBeanDefinition(beanName);
            String originalClassName = beanDefinition.getBeanClassName();
            try {
                if(isNull(originalClassName)) return;
                Class<?> originalClass = Class.forName(originalClassName);
                Method[] methods = originalClass.getMethods();
                for (Method method : methods) {
                    if(method.isAnnotationPresent(Get.class)
                            || method.isAnnotationPresent(Post.class)
                            || method.isAnnotationPresent(Put.class)
                            || method.isAnnotationPresent(Delete.class)) {
                        Object bean = context.getBean(beanName);
                        Method proxyMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                        Parameter[] parameters = proxyMethod.getParameters();
                        List<Object> args = new ArrayList<>();
                        for (Parameter parameter : parameters) {
                            if(parameter.getType().isPrimitive()){
                                args.add(0);
                            } else {
                                args.add(null);
                            }
                        }
                        proxyMethod.invoke(bean, args.toArray(new Object[0]));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
