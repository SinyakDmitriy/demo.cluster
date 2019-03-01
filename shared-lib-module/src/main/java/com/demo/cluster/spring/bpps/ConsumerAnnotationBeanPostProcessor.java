package com.demo.cluster.spring.bpps;

import com.demo.cluster.spring.annotations.Consumer;
import com.demo.cluster.spring.annotations.EventBusMarker;
import com.demo.cluster.spring.model.Argument;
import io.reactivex.Observable;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
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
public class ConsumerAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Autowired private ParameterNameDiscoverer parameterNameDiscoverer;
    private Map<String, Class> map = new HashMap<>();
    @Autowired private EventBus eventBus;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Class<?> beanClass = bean.getClass();
        if(beanClass.isAnnotationPresent(EventBusMarker.class)){
            map.put(beanName, beanClass);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class originalBeanClass = map.get(beanName);
        if(isNull(originalBeanClass)) return bean;

        return Proxy.newProxyInstance(
                originalBeanClass.getClassLoader(),
                originalBeanClass.getInterfaces(),
                (proxy, method, args) -> {
                    Method originalMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                    Consumer annotation = originalMethod.getDeclaredAnnotation(Consumer.class);
                    if(isNull(annotation)) return originalMethod.invoke(bean, args);

                    String addr = annotation.value();

                    eventBus.consumer(addr).handler(msg -> {
                        System.out.println(msg.body());
                        Map<String, Object> map = ((JsonObject) msg.body()).getMap();
                        Argument[] arguments = updateParameters(originalMethod);
                        List<Object> objs = new ArrayList<>();

                        for (Argument argument : arguments) {
                                if(argument.getType().isPrimitive()){
                                    objs.add(map.get(argument.getName()));
                                } else {
                                    objs.add(argument.getType().cast(map.get(argument.getName())));
                                }
                        }

                        try {
                            Observable.combineLatest(
                                    Observable.just(msg),
                                    (Observable) originalMethod.invoke(bean, objs.toArray(new Object[0])),
                                    (f, s) -> {
                                        System.out.println(s);
                                        Map<String, Object> _map = new HashMap<>();
                                        _map.put("response", s);
                                        f.reply(new JsonObject(_map));
                                        return "";
                                    }).subscribe();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                    return null;
                });
    }

    private Argument[] updateParameters(Method method){
        String[] names = parameterNameDiscoverer.getParameterNames(method);
        Parameter[] parameters = method.getParameters();
        List<Argument> list = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Argument arg = new Argument(names[i], parameters[i].getType(), null);
            list.add(arg);
        }

        return list.toArray(new Argument[0]);
    }
}
