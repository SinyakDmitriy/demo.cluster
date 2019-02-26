package com.demo.cluster;

import com.demo.cluster.web.Server;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        super.start();

        JsonObject jsonObject = Vertx.currentContext().config();
        Server server = new Server();
        server.start(vertx);

//        System.setProperty("properties", "application-" + Vertx.currentContext().config().getString("properties") + ".properties");
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.demo.cluster");

//        System.out.println(jsonObject.getInteger("version"));
    }
}
