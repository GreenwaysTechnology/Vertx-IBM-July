package com.ibm.vertx.core.serviceproxy;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

public class GreeterServiceImpl implements GreeterService {
  @Override
  public void sayHello(String name, Handler<AsyncResult<String>> handler) {
    handler.handle(Future.succeededFuture("Hello" + name));
  }
}
