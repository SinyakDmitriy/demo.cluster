package com.demo.cluster.eventbus.impl;

import com.demo.cluster.eventbus.IUserApi;
import com.demo.cluster.model.mysql.Role;
import com.demo.cluster.model.mysql.User;
import com.demo.cluster.spring.annotations.Api;
import com.demo.cluster.spring.annotations.AuthRole;
import com.demo.cluster.spring.annotations.Get;
import io.reactivex.Observable;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.springframework.stereotype.Component;

@Api
@Component
public class UserApi implements IUserApi {

    @AuthRole(Role.USER)
    @Get("/eventbus/user/get/user/auth")
    public Observable<User> getUser(HttpServerRequest request, HttpServerResponse response, User user) {
        System.out.println("user eventbus - get user");
        return Observable.just(new User("old user"));
    }

    //    @AuthRole(Role.USER)
    @Get("/eventbus/user/get/user")
    public Observable<User> getUser(HttpServerRequest request, HttpServerResponse response) {
        System.out.println("user eventbus - get user");
        return Observable.just(new User("old user"));
    }

}
