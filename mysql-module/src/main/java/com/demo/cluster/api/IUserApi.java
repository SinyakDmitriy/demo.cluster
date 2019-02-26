package com.demo.cluster.api;

import io.reactivex.Observable;

public interface IUserApi {

    Observable findUserById(int id, long _id);
}
