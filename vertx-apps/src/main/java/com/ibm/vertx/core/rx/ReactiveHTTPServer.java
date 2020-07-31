package com.ibm.vertx.core.rx;

import io.reactivex.Completable;
import io.vertx.example.util.Runner;
import io.vertx.reactivex.core.AbstractVerticle;

public class ReactiveHTTPServer extends AbstractVerticle {


  public static void main(String[] args) {
    Runner.runExample(ReactiveHTTPServer.class);
  }

  @Override
  public Completable rxStart() {
    super.rxStart();
    return vertx.createHttpServer()
      .requestHandler(req -> req.response().end("Hello World"))
      .rxListen(3000)
      .toCompletable();
  }
}
