package com.demo.cluster.repository.mysql.impl;

import com.demo.cluster.model.mysql.User;
import com.demo.cluster.repository.mysql.IUserRepository;
import io.reactivex.Observable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepository implements IUserRepository {

    @Override
    public Observable<List<User>> findAll() {
        List<User> users = new ArrayList<>();
        User user = new User("first"); users.add(user);
        user = new User("second"); users.add(user);
        user = new User("third"); users.add(user);

        return Observable.just(users);
    }
}
