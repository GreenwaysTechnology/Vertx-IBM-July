package com.ibm.vertx.core.asyncwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;


//how to avoid callback hell:
class ComplexCallback extends AbstractVerticle {
  //prepareDatabase
  public Future<Void> prepareDatabase() {
    System.out.println("PrepareDatabase is called");
    Promise promise = Promise.promise();
    promise.complete();
    return promise.future();
  }

  public Future<Void> startHttpServer() {
    System.out.println("startHttpServer is called");
    Promise promise = Promise.promise();
    promise.complete();
    return promise.future();
  }

  public Future<Void> startWebContainer() {
    System.out.println("startWebContainer is called");
    Promise promise = Promise.promise();
    promise.complete();
    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    prepareDatabase().onComplete(dbar -> {
      //if database success,start http server
      if (dbar.succeeded()) {
        startHttpServer().onComplete(httpar -> {
          //if http success, start web container
          if (httpar.succeeded()) {
            startWebContainer().onComplete(webctar -> {
              if (webctar.succeeded()) {
                System.out.println("System is Up!!");
              } else {
                System.out.println("System is down!!!");
              }
            });
          }
        });
      } else {
        System.out.println(dbar.cause().getMessage());
      }
    });

    //avoid callback hell using compose method
    prepareDatabase()
      .compose(httpar -> {
        System.out.println("Extra server logic");
        return startHttpServer();
      })
      .compose(webar -> startWebContainer())
      .onComplete(status -> {
        if (status.succeeded()) {
          System.out.println("All Server : Compose is Ready!");
        } else {
          System.out.println("Server is down");
        }
      });

    prepareDatabase()
      .compose(httpar -> {
        System.out.println("Extra server logic");
        return startHttpServer();
      })
      .compose(webar -> startWebContainer())
      .onSuccess(res -> System.out.println("Server is up"))
      .onFailure(err -> System.out.println("Server is down"));
  }
}


public class CallbackHellSolution extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(CallbackHellSolution.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new ComplexCallback());
  }
}
