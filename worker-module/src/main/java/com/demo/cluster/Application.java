package com.demo.cluster;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Application extends AbstractVerticle {

    private EventBus eb;
    private long key;

    @Override
    public void start() throws Exception {
        super.start();

        eb = vertx.eventBus();
        JsonObject jsonObject = Vertx.currentContext().config();
        key = jsonObject.getInteger("version");
        log.info("Application start, version -> {}", jsonObject.getInteger("version"));

        eb.consumer("request", this::consumer);
    }

    private void consumer(Message<JsonObject> msg){
        Map<String, Object> map = new HashMap<>();
        map.put("Worker_version", String.valueOf(key));
        map.put("Worker_thread", Thread.currentThread().getName());
        map.put("MSG", msg.body());
        JsonObject message = new JsonObject(map);
        eb.publish("response", message);
    }
}
