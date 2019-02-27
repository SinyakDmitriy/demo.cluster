package com.demo.cluster.spring.bpps;

import com.demo.cluster.model.mysql.Role;
import com.demo.cluster.spring.annotations.*;
import io.reactivex.Observable;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Component
public class ApiAnnotationBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class> apiMap = new HashMap<>();
    private Map<String, Object> interceptorMap = new HashMap<>();

    @Autowired private ApplicationContext context;
    @Autowired private Router router;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(Api.class)){
            apiMap.put(beanName, bean.getClass());
        }

        if(bean.getClass().isAnnotationPresent(Interceptor.class)){
            interceptorMap.put(beanName, bean);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class originalClass = apiMap.get(beanName);
        if(isNull(originalClass)) return bean;
        return Proxy.newProxyInstance(
                originalClass.getClassLoader(),
                originalClass.getInterfaces(),
                (proxy, method, args) -> {

                    Method originalMethod = originalClass.getMethod(method.getName(), method.getParameterTypes());
                    System.out.println("BEGIN");
                    if(originalMethod.isAnnotationPresent(Get.class)){
                        Get annotation = originalMethod.getAnnotation(Get.class);
                        Route route = router.get(annotation.value());
                        System.out.println(annotation);
                        addInterceptor(route, interceptorMap.values(), originalMethod);
                        addHandler(route, originalMethod, bean);
                    } else if(originalMethod.isAnnotationPresent(Post.class)){
                        Post annotation = originalMethod.getAnnotation(Post.class);
                        Route route = router.post(annotation.value());
                        addInterceptor(route, interceptorMap.values(), originalMethod);
                        addHandler(route, originalMethod, bean);
                    } else if(originalMethod.isAnnotationPresent(Put.class)){
                        Put annotation = originalMethod.getAnnotation(Put.class);
                        Route route = router.put(annotation.value());
                        addInterceptor(route, interceptorMap.values(), originalMethod);
                        addHandler(route, originalMethod, bean);
                    } else if(originalMethod.isAnnotationPresent(Delete.class)){
                        Delete annotation = originalMethod.getAnnotation(Delete.class);
                        Route route = router.delete(annotation.value());
                        addInterceptor(route, interceptorMap.values(), originalMethod);
                        addHandler(route, originalMethod, bean);
                    } else return method.invoke(bean, args);

                    return null;
                }
        );
    }

    private void addHandler(Route route, Method method, Object bean){
        route.handler(context -> {
            try {
                Object value = context.get("value");
                System.out.println(value);
                if(isNull(value)) ((Observable) method.invoke(bean, context.request(), context.response()))
                        .subscribe(r -> context.response().putHeader("Content-Type", "application/json").end(JsonObject.mapFrom(r).toBuffer()));
                else ((Observable) method.invoke(bean, context.request(), context.response(), context.get("value")))
                        .subscribe(r -> context.response().putHeader("Content-Type", "application/json").end(JsonObject.mapFrom(r).toBuffer()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private  void addInterceptor(Route route, Collection<Object> beans, Method method){
        AuthRole annotation = method.getAnnotation(AuthRole.class);
        System.out.println(annotation);
        System.out.println(beans);
        if(beans.isEmpty() || isNull(annotation)) return;

        for (Object bean : beans) {
            System.out.println(bean);
            route.handler(context -> {
                try {
                    Role role = annotation.value();
                    Method[] methods = bean.getClass().getMethods();
                    for (Method iMethod : methods) {
                        if(iMethod.getName().equals("intercept")) {
                            ((Observable) iMethod.invoke(bean, context.request(), context.response(), role))
                                    .subscribe(r -> {
                                        context.put("value", r);
                                        context.next();
                                    });
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
