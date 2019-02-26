package com.demo.cluster.spring.services;

import com.demo.cluster.spring.annotations.InitProxy;
import com.demo.cluster.spring.repository.IUserRepository;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessingService {

    @Autowired private IUserRepository userRepository;

    @InitProxy
    public void init(){
        userRepository.findAll()
                .flatMap(r -> Observable.fromIterable(r))
                .subscribe(r -> System.out.println(r.getName()));
    }

}
