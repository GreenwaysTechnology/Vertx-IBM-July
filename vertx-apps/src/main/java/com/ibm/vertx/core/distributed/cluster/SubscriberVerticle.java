package com.ibm.vertx.core.distributed.cluster;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class SubscriberVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    ClusterManager mgr = new HazelcastClusterManager();

    VertxOptions options = new VertxOptions().setClusterManager(mgr);

    Vertx.clusteredVertx(options, cluster -> {
      if (cluster.succeeded()) {
        //Here i want to replicate 10 copies of Subscriber Verticle and also enables built in load balancer
        //which scales automatically when verticles goes off.
        DeploymentOptions deploymentOptions = new DeploymentOptions().setInstances(10).setHa(true);

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

  private void consumeNews() {
    EventBus eventBus = vertx.eventBus();
    //Declare Consumer
    MessageConsumer<String> consumer = eventBus.consumer("news.in.covid");
    //handle/process the message/news
    consumer.handler(news -> {
      RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
      String jvmName = runtimeBean.getName();
      System.out.println("JVM Name = " + jvmName);
      long pid = Long.valueOf(jvmName.split("@")[0]);
      System.out.println("PID  = " + pid + " Thread = " + Thread.currentThread().getName());
      System.out.println("News 7's Today News : " + news.body());
    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    consumeNews();
  }
}
