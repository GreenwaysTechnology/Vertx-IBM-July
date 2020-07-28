package com.ibm.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;

public class VertxInstanceCreationUsingAbstractVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Runner.runExample(VertxInstanceCreationUsingAbstractVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Hello Vertx");
    System.out.println(vertx.toString());
  }

}
