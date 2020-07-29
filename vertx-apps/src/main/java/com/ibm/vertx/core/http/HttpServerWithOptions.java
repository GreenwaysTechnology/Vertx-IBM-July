package com.ibm.vertx.core.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.example.util.Runner;

public class HttpServerWithOptions extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(HttpServerWithOptions.class);
  }

  @Override
  public void start() throws Exception {
    super.start();

    //HttpServer options
    HttpServerOptions options = new HttpServerOptions().setPort(3002);
    //options.setPort(3002);
    //create WebServer , Handle request  and Send Response
    HttpServer server = vertx.createHttpServer(options);
    //Handle Request
    server.requestHandler(context -> {
      //context is container object , having HttpResponse and HttpRequest.
      HttpServerResponse response = context.response();
      response.end("<h1>Hello Vertx Application</h1>");
    });


    //start server
    server.listen(serverHandler -> {
      if (serverHandler.succeeded()) {
        System.out.println("Http Server is up and running");
      } else {
        System.out.println(serverHandler.cause().getMessage());
      }
    });
  }
}
