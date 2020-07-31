package com.ibm.vertx.core.serviceproxy;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

@ProxyGen
@VertxGen
public interface GreeterService {
  //event bus address
  public static String ADDRESS = GreeterService.class.getName();

  //api to create Service Proxy Object
  static GreeterService createProxy(Vertx vertx, String address) {
    return new GreeterServiceVertxEBProxy(vertx, address);
  }

  //biz apis
  void sayHello(String name, Handler<AsyncResult<String>> handler);
}
