package com.demo.cluster.web;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Server {

    private int port = 8080;
    
    public void start(Vertx vertx){
        HttpServer server = vertx.createHttpServer();
        EventBus eb = vertx.eventBus();

        server.requestHandler(request -> {

            // This handler gets called for each request that arrives on the server
            HttpServerResponse response = request.response();
            response.putHeader("content-type", "text/plain");
            Map<String, Object> map = new HashMap<>();
            map.put("id", 25);
            map.put("_id", 25L);
            JsonObject msg = new JsonObject(map);
            eb.send("find.user.by.id", msg, ar -> {
                if(ar.succeeded()) {
                    JsonObject body = (JsonObject) ar.result().body();
                    log.info("response -> {}", body);
                }
            });
            log.info("send message -> {}", msg);
            // Write to the response and end it
            response.end("Hello World! key ->" + 1 + "  " + Thread.currentThread().getName());
        });

        server.listen(port);
        log.info("Server start on port -> {}", port);

    }
}
