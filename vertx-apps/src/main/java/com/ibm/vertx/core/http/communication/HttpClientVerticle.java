package com.ibm.vertx.core.http.communication;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.example.util.Runner;

class ClientVerticle extends AbstractVerticle {

  private void startRequest() {
    HttpClientOptions options = new HttpClientOptions();
    HttpClient client = vertx.createHttpClient(options);
    client.request(HttpMethod.GET, 3001, "localhost", "/", res -> {
      System.out.println("Status : " + res.statusCode());
      //handle response /result
      res.bodyHandler(payload -> {
        System.out.println(payload);
      });
    }).end();

  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.setTimer(1000, ar -> {
      startRequest();
    });
  }
}


public class HttpClientVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(HttpClientVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new ClientVerticle());
  }
}
