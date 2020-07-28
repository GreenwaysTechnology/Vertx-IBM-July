package com.ibm.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.example.util.Runner;


class GreeterVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Greeter Verticle!!!");
  }

}

//main verticle
public class HelloWorldVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(HelloWorldVerticle.class);
  }

  //override start method
  // @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Hello World  Verticle");
    //deploy greeter verticle
    vertx.deployVerticle(new GreeterVerticle());

  }
}
//way -2
//public class HelloWorldVerticle {
//  public static void main(String[] args) {
//    Vertx vertx = Vertx.vertx();
//    //deploy verticle
//    vertx.deployVerticle(new GreeterVerticle());
//  }
//}


