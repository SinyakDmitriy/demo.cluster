package com.demo.cluster.services;

import com.demo.cluster.model.mysql.User;
import io.reactivex.Observable;

public interface IUserService {

    Observable<User> findUserById(Integer id);
}
