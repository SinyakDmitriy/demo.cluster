package com.demo.cluster.spring.annotations;

import com.demo.cluster.spring.InterfaceScanRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({InterfaceScanRegistrar.class})
public @interface InterfaceScaner {
    String[] value() default {};
}
