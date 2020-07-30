package com.ibm.vertx.core.distributed.cluster;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.example.util.Runner;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;


public class ClusterManagerApp extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ClusterManagerApp.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    ClusterManager mgr = new HazelcastClusterManager();

    VertxOptions options = new VertxOptions().setClusterManager(mgr);

    Vertx.clusteredVertx(options, cluster -> {
      if (cluster.succeeded()) {
        DeploymentOptions deploymentOptions = new DeploymentOptions().setInstances(1).setHa(true);
        cluster.result().deployVerticle("com.ibm.vertx.core.distributed.cluster.PublisherVerticle", deploymentOptions, res -> {
          if (res.succeeded()) {
            System.out.println("Deployment id is: " + res.result());
          } else {
            System.out.println("Deployment failed!");
          }
        });
        cluster.result().deployVerticle("com.ibm.vertx.core.distributed.cluster.SubscriberVerticle", deploymentOptions, res -> {
          if (res.succeeded()) {
            System.out.println("Deployment id is: " + res.result());
          } else {
            System.out.println("Deployment failed!");
          }
        });
      } else {
        System.out.println("Cluster up failed: " + cluster.cause());
      }
    });
  }
}
