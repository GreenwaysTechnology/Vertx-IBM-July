package com.ibm.vertx.core.http.communication;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

class HttpServerVerticle extends AbstractVerticle {

  private void startApp() {
    //HttpServer options
    HttpServerOptions options = new HttpServerOptions();
    options.setPort(3001);
    //create WebServer , Handle request  and Send Response

    HttpServer server = vertx.createHttpServer(options);

    //Handle Request
    server.requestHandler(context -> {
      //context is container object , having HttpResponse and HttpRequest.
      HttpServerResponse response = context.response();
      response.putHeader("content-type", "application/json");
      JsonObject address = new JsonObject();
      address.put("city", "coimbatore").put("street", "10th street").put("state", "Tamil Nadu");
      JsonObject visitor = new JsonObject();
      visitor.put("name", "subramanian").put("address", address);
      //response.end("<h1>Hello Vertx Application</h1>");
      response.end(visitor.encodePrettily());
    });
    server.listen(serverHandler -> {
      if (serverHandler.succeeded()) {
        System.out.println("Http Server is up and running");
      } else {
        System.out.println(serverHandler.cause().getMessage());
      }
    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    startApp();
  }
}


public class ServerVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ServerVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new HttpServerVerticle());
  }
}
