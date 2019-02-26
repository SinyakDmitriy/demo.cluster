package com.demo.cluster.spring.repository;

import com.demo.cluster.spring.model.User;
import io.reactivex.Observable;

import java.util.List;

public interface IUserRepository {

    Observable<List<User>> findAll();
}
