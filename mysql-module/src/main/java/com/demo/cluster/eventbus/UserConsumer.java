package com.demo.cluster.eventbus;

import com.demo.cluster.constants.EventBusConstants;
import com.demo.cluster.services.IUserService;
import com.demo.cluster.spring.annotations.EventBusMarker;
import com.demo.cluster.spring.annotations.Consumer;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@EventBusMarker
@Slf4j
@Component
public class UserConsumer implements IUserConsumer, EventBusConstants {
    @Autowired private IUserService userService;

    @Consumer(FIND_USER_REQUEST_URL)
    public Observable findUserById(int id, long _id){
        return userService.findUserById(id);
    }
}
