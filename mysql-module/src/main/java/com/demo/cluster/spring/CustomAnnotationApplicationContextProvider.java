package com.demo.cluster.spring;

import io.vertx.core.Vertx;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CustomAnnotationApplicationContextProvider {
    private final Vertx vertx;

    public CustomAnnotationApplicationContextProvider(Vertx vertx) {
        this.vertx = vertx;
    }

    private void configureBeans(ConfigurableListableBeanFactory beanFactory){
        beanFactory.registerSingleton(vertx.getClass().getName(), vertx);
    }

    public AnnotationConfigApplicationContext get(){
        return new AnnotationConfigApplicationContext(){
            @Override
            protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
                super.postProcessBeanFactory(beanFactory);
                configureBeans(beanFactory);
            }
        };
    }

    public AnnotationConfigApplicationContext get(DefaultListableBeanFactory beanFactory) {
        return new AnnotationConfigApplicationContext(beanFactory) {

            @Override
            protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
                super.postProcessBeanFactory(beanFactory);
                configureBeans(beanFactory);
            }
        };
    }

    public AnnotationConfigApplicationContext get(Class<?>... annotatedClasses) {
        return new AnnotationConfigApplicationContext(annotatedClasses) {

            @Override
            protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
                super.postProcessBeanFactory(beanFactory);
                configureBeans(beanFactory);
            }
        };
    }

    public AnnotationConfigApplicationContext get(String... basePackages) {
        return new AnnotationConfigApplicationContext(basePackages) {

            @Override
            protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
                super.postProcessBeanFactory(beanFactory);
                configureBeans(beanFactory);
            }
        };
    }
}
