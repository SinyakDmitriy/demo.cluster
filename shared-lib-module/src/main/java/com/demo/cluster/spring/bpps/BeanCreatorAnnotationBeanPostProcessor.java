package com.demo.cluster.spring.bpps;

import com.demo.cluster.spring.model.Empty;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public abstract class BeanCreatorAnnotationBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> map = new HashMap<>();

    @Autowired private ApplicationContext context;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().equals(Empty.class)) {
            Class clazz = ((Empty) bean).getClazz();
            map.put(beanName, clazz);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
       return bean;
    }

    protected Class getClassByBeanName(String beanName){
        return map.get(beanName);
    }
    protected Map<String, Class> getMap(){
        return map;
    }
}
