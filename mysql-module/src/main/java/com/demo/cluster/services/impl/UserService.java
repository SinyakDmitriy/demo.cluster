package com.demo.cluster.services.impl;

import com.demo.cluster.model.mysql.User;
import com.demo.cluster.services.IUserService;
import io.reactivex.Observable;
import io.vertx.core.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService implements IUserService {

    @Autowired private EventBus eventBus;

    @Override
    public Observable<User> findUserById(Integer id) {
        return Observable.just(new User("fourth"));
    }
}
