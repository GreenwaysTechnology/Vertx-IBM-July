package com.ibm.vertx.core.distributed.cluster;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;


public class HighAvailbilityApp extends AbstractVerticle {

  @Override
  public void start() {

    HttpServer server = vertx.createHttpServer();

    server.requestHandler(req -> {
      long id = Thread.currentThread().getId();
      req.response().end("<h1> I am coming from " + id + " Instance");
    });

    server.listen(8888, "localhost", handler -> {
      if (handler.succeeded()) {
        System.out.println("Server is Ready! " + Thread.currentThread().getId());
      } else {
        System.out.println("Server failed to Start");
      }
    });
  }


}
