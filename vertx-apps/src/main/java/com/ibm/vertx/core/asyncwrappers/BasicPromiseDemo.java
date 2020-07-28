package com.ibm.vertx.core.asyncwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class PromiseVerticle extends AbstractVerticle {

  private Future<Void> getEmptyPromise() {
    //Create Promise Object ; using factory method
    Promise promise = Promise.promise();
    //send empty success result
    promise.complete();
    //return future: convert Promise to
    return promise.future();
  }

  //how to send success or error based on biz logic.
  public Future<String> login() {
    //Create Future Object ; using factor method
    Promise promise = Promise.promise();
    //biz logic
    String userName = "admin";
    String password = "admin";
    if (userName.equals("admin") && password.equals("admin")) {
      promise.complete("Login Success");
    } else {
      promise.fail("Login failed");
    }
    //return future
    return promise.future();

  }

  @Override
  public void start() throws Exception {
    super.start();
    //Promise.future()-will convert Promise into future
    //getEmptyPromise().future().succeeded();
    String result = getEmptyPromise().succeeded() ? "Success" : "Failed";
    System.out.println(result);

    login().onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause().getMessage());
      }
    });
    //chaining : fluent pattern
    login().onSuccess(System.out::println).onFailure(System.out::println);

  }
}

public class BasicPromiseDemo extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BasicPromiseDemo.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new PromiseVerticle());
  }
}
