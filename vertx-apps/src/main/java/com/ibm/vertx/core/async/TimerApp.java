package com.ibm.vertx.core.async;

import io.vertx.core.*;
import io.vertx.example.util.Runner;


class Timer extends AbstractVerticle {

  //return result in async way
  public Future<String> delay(long timeout) {
    Promise promise = Promise.promise();
    //async operations
    vertx.setTimer(timeout, handler -> {
      //wrap result after timeout
      promise.complete("I am delayed Message : My time out " + timeout);
    });
    return promise.future();

  }

  /**
   * This method cant return Future/Promise
   * because this method once returns, -completes and removed from memory
   * in second tick, it tries to look promise and return , there is no such implementation then
   * it will throw illagalstateException
   */
  public void tick(long timeout) {
    //async operations
    vertx.setPeriodic(timeout, handler -> {
      //wrap result after timeout
      System.out.println("tick  at every  " + timeout + "ms");
    });
  }

  //callback version
  public void heartBeat(Handler<AsyncResult<String>> aHandler) {
    //return result after some time
    long timerId = vertx.setPeriodic(1000, ar -> {
      //wrap the result into Future
      aHandler.handle(Future.succeededFuture("heart bea at every 1 sec"));
    });
    vertx.setTimer(10000, ar1 -> {
      System.out.println("canceling");
      vertx.cancelTimer(timerId);
      System.exit(1);
    });
  }


  //sync api
  private String status(String message) {
    return message;
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println(status("start"));
    delay(5000).onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      }
    });
    delay(1000).onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      }
    });
    //tick(1000);
    //the callback function is called for every timer iteration
    heartBeat(asyncResult -> {
      System.out.println(asyncResult.result());
    });
    System.out.println(status("end"));

  }
}


public class TimerApp extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(TimerApp.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new Timer());
  }
}
