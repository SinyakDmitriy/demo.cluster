package com.demo.cluster.interceptors;

import com.demo.cluster.model.mysql.Role;
import io.reactivex.Observable;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

public interface IInterceptor {

    Observable intercept(HttpServerRequest request, HttpServerResponse response, Role role);
}
