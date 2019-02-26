package com.demo.cluster.api;

import com.demo.cluster.services.IUserService;
import com.demo.cluster.spring.annotations.Api;
import com.demo.cluster.spring.annotations.EventBus;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Api
@Slf4j
@Component
public class UserApi implements IUserApi {
    @Autowired private IUserService userService;

    @EventBus("find.user.by.id")
    public Observable findUserById(int id, long _id){
        return userService.findUserById(id);
    }
}
