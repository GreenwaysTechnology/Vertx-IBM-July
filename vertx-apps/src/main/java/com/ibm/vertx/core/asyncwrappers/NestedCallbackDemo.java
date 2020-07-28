package com.ibm.vertx.core.asyncwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class CallbackNestingVerticle extends AbstractVerticle {

  //getUser
  public Future<String> getUser() {
    Promise promise = Promise.promise();
    //biz logic
    String fakeUser = "Subramanian";
    if (fakeUser != null) {
      promise.complete(fakeUser);
    } else {
      promise.fail("User Not Found!!");
    }

    return promise.future();
  }

  //login
  public Future<String> login(String userName) {
    Promise promise = Promise.promise();
    //biz logic
    if (userName.equals("Subramanian")) {
      promise.complete("Login success ");
    } else {
      promise.fail("Login failed!!");
    }

    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    getUser().onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println("Get User is called");
        //nested callback
        login(asyncResult.result()).onComplete(asyncResult1 -> {
          System.out.println("Login is called");
          if (asyncResult1.succeeded()) {
            System.out.println(asyncResult1.result());
          } else {
            System.out.println(asyncResult1.cause().getMessage());
          }
        });
      } else {
        System.out.println(asyncResult.cause().getMessage());
      }
    });
  }
}


public class NestedCallbackDemo extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(NestedCallbackDemo.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new CallbackNestingVerticle());
  }
}
