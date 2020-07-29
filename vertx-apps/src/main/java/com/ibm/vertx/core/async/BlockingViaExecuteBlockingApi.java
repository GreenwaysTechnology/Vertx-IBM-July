package com.ibm.vertx.core.async;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Context;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class OffLoadVerticle extends AbstractVerticle {

  public void delay() {
    vertx.setPeriodic(5000, handler -> {
      //Blocking event loop thread and get result from blocking api, handover to non blocking api
      vertx.executeBlocking(this::sayHello, this::resultHandler);
    });
  }



  //blocking api
  private void sayHello(Promise<String> promise) {
    System.out.println("Say Hello : " + Thread.currentThread().getName());
    try {
      Thread.sleep(4000);
      System.out.println("Wake Up read to send data to Non blocking Service");
      promise.complete("Hey this is blocking Result");
    } catch (InterruptedException es) {
      promise.fail("Something went wrong in blocking service");
    }
  }

  //read result from blocking service
  private void resultHandler(AsyncResult<String> ar) {
    System.out.println("Result Handler" + Thread.currentThread().getName());
    if (ar.succeeded()) {
      System.out.println("Blocking api Result goes Ready Here");
      System.out.println(ar.result());
    } else {
      System.out.println(ar.cause().getMessage());
    }
  }

  @Override
  public void start() throws Exception {
    super.start();
    delay();
  }
}

public class BlockingViaExecuteBlockingApi extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BlockingViaExecuteBlockingApi.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new OffLoadVerticle());
  }
}
