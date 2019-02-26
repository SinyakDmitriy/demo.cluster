package com.demo.cluster.repository.mysql;

import com.demo.cluster.model.mysql.User;
import io.reactivex.Observable;

import java.util.List;

public interface IUserRepository {

    Observable<List<User>> findAll();
}
