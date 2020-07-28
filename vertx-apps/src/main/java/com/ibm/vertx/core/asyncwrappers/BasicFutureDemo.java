package com.ibm.vertx.core.asyncwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.example.util.Runner;

//Fututure Verticle

class FutureVerticle extends AbstractVerticle {

  //Future flavours
  //return empty Future
  private Future<Void> getEmptyFuture() {
    //Create Future Object ; using factor method
    Future future = Future.future();
    //send empty success result
    future.complete();
    //return future
    return future;
  }

  //How to return always success Result
  private Future<String> getDataFuture() {
    //Create Future Object ; using factor method
    Future future = Future.future();
    //send empty success result
    future.complete("Hello I am From Future");
    //return future
    return future;
  }

  //failures
  private Future<String> getErrorFuture() {
    //Create Future Object ; using factor method
    Future future = Future.future();
    //send empty success result
    future.fail("something went wrong!!!");
    //return future
    return future;
  }

  //how to send success or error based on biz logic.
  public Future<String> login() {
    //Create Future Object ; using factor method
    Future future = Future.future();

    //biz logic
    String userName = "admin";
    String password = "admin";
    if (userName.equals("admin") && password.equals("admin")) {
      future.complete("Login Success");
    } else {
      future.fail("Login failed");
    }
    //return future
    return future;

  }

  @Override
  public void start() throws Exception {
    super.start();
    //caller
    //Test Empty Future
    if (getEmptyFuture().succeeded()) {
      System.out.println("Empty future is succeed");
    } else {
      System.out.println("Future failed");
    }
    //Grab Data via Future

    getDataFuture().setHandler(new Handler<AsyncResult<String>>() {
      @Override
      public void handle(AsyncResult<String> asyncResult) {
        //Test your operation is success or failed
        if (asyncResult.succeeded()) {
          System.out.println(asyncResult.result());
        } else {
          System.out.println(asyncResult.cause().getMessage());
        }
      }
    });
    //
    getDataFuture().setHandler(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause().getMessage());
      }
    });
    //using oncomplete
    getDataFuture().onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause().getMessage());
      }
    });

    //handle failures
    getErrorFuture().onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause().getMessage());
      }
    });

    //handle both success or failure

    login().onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause().getMessage());
      }
    });

    //only success
    getDataFuture().onSuccess(result -> System.out.println(result));
    getDataFuture().onSuccess(System.out::println);

    //only errors
    getErrorFuture().onFailure(err -> System.out.println(err));
    getErrorFuture().onFailure(System.out::println);

    //chaining : fluent pattern
    login().onSuccess(System.out::println).onFailure(System.out::println);


  }
}


public class BasicFutureDemo extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BasicFutureDemo.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new FutureVerticle());
  }
}
