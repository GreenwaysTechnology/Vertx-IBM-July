package com.ibm.vertx.core.async;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.example.util.Runner;


public class BlockingVerticleUsingWorkers extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BlockingVerticleUsingWorkers.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //deploy standard verticle pattern
    //   vertx.deployVerticle(new WorkerVerticle());
    //deploy verticle as worker verticle
    //normal java pattern
//    DeploymentOptions options = new DeploymentOptions();
//    options.setWorker(true);

    //Vertx fluent api pattern
//    DeploymentOptions options = new DeploymentOptions().setWorker(true);
//    vertx.deployVerticle("com.ibm.vertx.core.async.WorkerVerticle", options);
    // DeploymentOptions options = new DeploymentOptions().setWorker(true);
    //factory : recommended
    vertx.deployVerticle("com.ibm.vertx.core.async.WorkerVerticle", new DeploymentOptions().setWorker(true));
    //vertx.deployVerticle(new WorkerVerticle(), new DeploymentOptions().setWorker(true) );
  }
}
