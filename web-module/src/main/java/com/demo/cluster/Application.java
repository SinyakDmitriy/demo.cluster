package com.demo.cluster;

import com.demo.cluster.spring.CustomAnnotationApplicationContextProvider;
import com.demo.cluster.web.Server;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class Application extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        super.start();

        System.setProperty("properties", "application-" + Vertx.currentContext().config().getString("properties") + ".properties");
        new CustomAnnotationApplicationContextProvider(vertx).get("com.demo.cluster");
    }
}
