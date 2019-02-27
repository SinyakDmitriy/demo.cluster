package com.demo.cluster.api;

import com.demo.cluster.model.mysql.User;
import io.reactivex.Observable;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

public interface IUserApi {

    Observable<User> getUser(HttpServerRequest request, HttpServerResponse response, User user);
    Observable<User> getUser(HttpServerRequest request, HttpServerResponse response);
}
