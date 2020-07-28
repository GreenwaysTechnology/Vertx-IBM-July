package com.ibm.vertx.core.asyncwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.example.util.Runner;

class UserApp extends AbstractVerticle {

  public void getUser(Handler<AsyncResult<String>> aHandler) {
    String fakeUser = "Subramanian";
    //biz logic
    if (fakeUser != null) {
      //handle success
      /**
       * asyncResult -> {
       *       if (asyncResult.succeeded()) {
       *         System.out.println(asyncResult.result());
       *       } else {
       *         System.out.println(asyncResult.cause().getMessage());
       *       }
       *
       *     }
       *     invocation
       */
      aHandler.handle(Future.succeededFuture(fakeUser));
    } else {
      aHandler.handle(Future.failedFuture("No User Found"));
    }

  }

  public void login(String userName, Handler<AsyncResult<String>> aHandler) {
    //biz logic
    if (userName.equals("Subramanian")) {
      //handle success
      aHandler.handle(Future.succeededFuture("Login Success"));
    } else {
      aHandler.handle(Future.failedFuture("Login failed"));
    }
  }

  public void showUserPage(String loginStatus, Handler<AsyncResult<String>> aHandler) {
    //biz logic
    if (loginStatus.equals("Login Success")) {
      //handle success
      aHandler.handle(Future.succeededFuture("Perumium User"));
    } else {
      aHandler.handle(Future.failedFuture("Guest"));
    }
  }

  @Override
  public void start() throws Exception {
    super.start();
    //callback function
    getUser(asyncResult -> {
      System.out.println("get user.....");
      if (asyncResult.succeeded()) {
        login(asyncResult.result(), asyncResult1 -> {
          System.out.println("login......");
          if (asyncResult1.succeeded()) {
            //System.out.println(asyncResult1.result());
            //showUser page
            showUserPage(asyncResult1.result(), asyncResult2 -> {
              System.out.println("user page.....");
              if (asyncResult2.succeeded()) {
                System.out.println(asyncResult2.result());
              } else {
                System.out.println(asyncResult2.cause().getMessage());
              }
            });
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


public class NestedCallbackWithouOnComplete extends AbstractVerticle {


  public static void main(String[] args) {
    Runner.runExample(NestedCallbackWithouOnComplete.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new UserApp());
  }
}
