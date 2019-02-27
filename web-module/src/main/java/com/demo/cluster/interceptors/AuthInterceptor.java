package com.demo.cluster.interceptors;

import com.demo.cluster.model.mysql.Role;
import com.demo.cluster.model.mysql.User;
import com.demo.cluster.spring.annotations.Interceptor;
import io.reactivex.Observable;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.springframework.stereotype.Component;

@Interceptor
@Component
public class AuthInterceptor implements IInterceptor {

    @Override
    public Observable intercept(HttpServerRequest request, HttpServerResponse response, Role role) {
        System.out.println("interceptor");
        return Observable.just(new User("interceptor"));
    }
}
