package com.demo.cluster.spring.bpps;

import com.demo.cluster.spring.annotations.Sender;
import com.demo.cluster.spring.model.Argument;
import com.google.common.util.concurrent.SettableFuture;
import io.reactivex.Observable;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

@Component
public class SenderAnnotationBeanPostProcessor extends BeanCreatorAnnotationBeanPostProcessor {

    @Autowired private ParameterNameDiscoverer parameterNameDiscoverer;
    @Autowired private EventBus eventBus;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        super.postProcessBeforeInitialization(bean, beanName);
        Class clazz = getClassByBeanName(beanName);
        if(isNull(clazz)) return bean;

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((InvocationHandler) (o, method, objects) -> {
            Method originalMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
            Sender annotation = originalMethod.getDeclaredAnnotation(Sender.class);
            if(isNull(annotation)) return originalMethod.invoke(bean, objects);

            String addr = annotation.value();
            SettableFuture future = SettableFuture.create();
            Argument[] arguments = updateParameters(originalMethod, objects);

            Map<String, Object> argsMap = new HashMap<>();

            for (Argument argument : arguments) {
                System.out.println(argument);
                argsMap.put(argument.getName(), argument.getValue());
            }

            eventBus.send(addr, new JsonObject(argsMap), response -> {
                System.out.println(response.result().body());
                future.set(response.result().body());
            });

            return Observable.fromFuture(future);
        });
        Object _obj = enhancer.create();
        System.out.println(clazz.cast(_obj).getClass().getName());
        return clazz.cast(_obj);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private Argument[] updateParameters(Method method, Object[] args){
        String[] names = parameterNameDiscoverer.getParameterNames(method);
        Parameter[] parameters = method.getParameters();
        List<Argument> list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Argument arg = new Argument(names[i], parameters[i].getType(), args[i]);
            list.add(arg);
        }

        return list.toArray(new Argument[0]);
    }
}
