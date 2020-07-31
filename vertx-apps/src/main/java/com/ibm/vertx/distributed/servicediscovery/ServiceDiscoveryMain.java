package com.ibm.vertx.distributed.servicediscovery;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.client.WebClient;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.HttpEndpoint;

class ServiceDiscoveryVerticle extends AbstractVerticle {

  private void publishServices() {
    //ServiceDiscovery Options which helps configure zoo keeper information
    ServiceDiscoveryOptions discoveryOptions = new ServiceDiscoveryOptions();
    //enable discovery server : apache zoo keeper
    discoveryOptions.setBackendConfiguration(new JsonObject()
      .put("connection", "127.0.0.1:2181")
      .put("ephemeral", true)
      .put("guaranteed", true)
      .put("basePath", "/services/my-backend")
    );
    //Service Discovery Instance creation which helps publish , discover and unpublish
    ServiceDiscovery discovery = ServiceDiscovery.create(vertx, discoveryOptions);

    //Create Record
    Record httpEndPointRecord = HttpEndpoint
      .createRecord("http-posts-service",
        true, "jsonplaceholder.typicode.com", 443, "/posts", new JsonObject());

    //publish HttpEndpoint
    discovery.publish(httpEndPointRecord, ar -> {
      if (ar.succeeded()) {
        System.out.println("Successfully published to Zookeeper...>>>>" + ar.result().toJson());
      } else {
        System.out.println(" Not Published " + ar.cause());
      }
    });
    //Reterive the Service
    vertx.setTimer(2000, ar -> {

      //Get The Service Instance from the Service
      HttpEndpoint.getWebClient(discovery, new JsonObject().put("name", "http-posts-service"), sar -> {
        WebClient client = sar.result();
          client.get("/posts").send(res -> {
          System.out.println("Response is ready!");
          System.out.println(res.result().bodyAsJsonArray().encodePrettily());
          //remove /release discovery record
          ServiceDiscovery.releaseServiceObject(discovery, client);
        });

      });

    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    publishServices();
  }
}


public class ServiceDiscoveryMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ServiceDiscoveryMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new ServiceDiscoveryVerticle());
  }
}
