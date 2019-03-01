package com.demo.cluster.services;

import com.demo.cluster.constants.EventBusConstants;
import com.demo.cluster.model.mysql.User;
import com.demo.cluster.spring.annotations.EventBusMarker;
import com.demo.cluster.spring.annotations.Sender;
import io.reactivex.Observable;

@EventBusMarker
public interface UserService extends EventBusConstants {

    @Sender(FIND_USER_REQUEST_URL)
    Observable<User> findUser(long id);
}
