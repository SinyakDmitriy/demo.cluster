package com.demo.cluster.eventbus;

import io.reactivex.Observable;

public interface IUserConsumer {

    Observable findUserById(int id, long _id);
}
