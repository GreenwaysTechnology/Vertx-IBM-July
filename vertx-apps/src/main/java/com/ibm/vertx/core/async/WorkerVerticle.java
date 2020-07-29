package com.ibm.vertx.core.async;

import io.vertx.core.AbstractVerticle;

public class WorkerVerticle extends AbstractVerticle {


  @Override
  public void start() throws Exception {
    super.start();
    System.out.println(Thread.currentThread().getName());
    //I am going block event loop.
    vertx.setPeriodic(10000, id -> {
      //Block Event loop thread.
      try {
        System.out.println("zzzzz");
        Thread.sleep(8000);
        System.out.println("wake up");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }
}
