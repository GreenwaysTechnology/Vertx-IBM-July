package com.ibm.vertx.core.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;

public class BasicHttpServer extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BasicHttpServer.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
//    HttpServer httpServer = vertx.createHttpServer();
//    //handle request and reponse
//    httpServer.requestHandler(request -> {
//      //send response
//      HttpServerResponse response = request.response();
//      //send /write response  and close the stream together
//      response.end("<h1>Hello Vertx Web Server!!</h1>");
//    });
//   //start server
//    httpServer.listen(3000, httpServerAsyncResult -> {
//      System.out.println("Vertx - HTTP Server is Ready!!!");
//    });
    //Fluent pattern
    vertx.createHttpServer()
      .requestHandler(request -> {
        //send response
        request.response().end("<h1>Hello Vertx Web Server!!</h1>");
      })
      .listen(3000, httpServerAsyncResult -> {
        System.out.println("Vertx - HTTP Server is Ready!!!");
      });

  }
}
