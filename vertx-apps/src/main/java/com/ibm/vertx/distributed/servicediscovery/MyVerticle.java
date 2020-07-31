package com.ibm.vertx.distributed.servicediscovery;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.servicediscovery.types.HttpEndpoint;


class CircuitBreakerVerticle extends BaseMicroServiceVerticle {

  @Override
  public void start() throws Exception {
    super.start();
    circuitBreaker.executeWithFallback(
      future -> {
        WebClient client = WebClient.create(vertx);
//// Send a GET request
        client
          .get("jsonplaceholder.typicode.com", "/postsxxx")
          .send(ar -> {
            // Obtain response
            HttpResponse<Buffer> response = ar.result();
            if (response.statusCode() != 200) {
              future.fail("HTTP error");
            } else {
              future.complete(response.bodyAsJsonArray().encode());
            }

          });
      }, v -> {
        // Executed when the circuit is opened
        return "Hello, I am fallback";
      })
      .onComplete(ar -> {
        // Do something with the result
        if (ar.succeeded()) {
          System.out.println(ar.result());
        } else {
          System.out.println(ar.cause());

        }
      });
  }
}

class PostVerticle extends BaseMicroServiceVerticle {

  @Override
  public void start() throws Exception {
    super.start();
    //im memory configurtion
    config().put("api.name", "posts");
    publishHttpEndpoint("posts-services", true, "jsonplaceholder.typicode.com", 443);

    config().put("api.name", "users");
    publishHttpEndpoint("users-services", true, "jsonplaceholder.typicode.com", 443);

    vertx.setTimer(5000, res -> {
      //posts
      HttpEndpoint.getWebClient(discovery, new JsonObject().put("name", "posts-services"), ar -> {
        if (ar.succeeded()) {
          WebClient client = ar.result();
          client.get("/" + config().getString("api.name")).send(response -> {
            System.out.println("Response is ready!");
            System.out.println(response.succeeded());
            System.out.println(response.result().bodyAsJsonArray().encodePrettily());
          });
        }
      });
    });
    vertx.setTimer(6000, res -> {
      //posts
      HttpEndpoint.getWebClient(discovery, new JsonObject().put("name", "users-services"), ar -> {
        if (ar.succeeded()) {
          WebClient client = ar.result();
          client.get("/" + config().getString("api.name")).send(response -> {
            System.out.println("Response is ready!");
            System.out.println(response.succeeded());
            System.out.println(response.result().bodyAsJsonArray().encodePrettily());
          });
        }
      });
    });
  }
}

public class MyVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(MyVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    // vertx.deployVerticle(new PostVerticle());
    vertx.deployVerticle(new CircuitBreakerVerticle());
  }
}
