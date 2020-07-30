package com.ibm.vertx.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.Router;

class HelloController extends AbstractVerticle {

  //ROUTER COnfiguration
  public Router getHelloRouterConfig() {
    Router router = Router.router(vertx);
    //rest end point happing
    router.get("/hello").handler(routingContext -> {
      routingContext.response().end("Hello");
    });
    router.get("/helloagain").handler(routingContext -> {
      routingContext.response().end("Hello Again");
    });

    return router;
  }

  @Override
  public void start() throws Exception {
    super.start();
  }
}

class HaiController extends AbstractVerticle {

  //ROUTER COnfiguration
  public Router getHaiRouterConfig() {
    Router router = Router.router(vertx);
    //rest end point happing
    router.get("/hai").handler(routingContext -> {
      routingContext.response().end("Hai");
    });
    router.get("/haiagain").handler(routingContext -> {
      routingContext.response().end("Hai Again");
    });

    return router;
  }

  @Override
  public void start() throws Exception {
    super.start();
  }
}

public class WebApplication extends AbstractVerticle {

  HttpServer server;
  HttpServerOptions options;

  public static void main(String[] args) {
    Runner.runExample(WebApplication.class);
  }

  public void StartApp() {
    options = new HttpServerOptions().setPort(3001).setHost("localhost");
    server = vertx.createHttpServer(options);

    //Router Registeration
    Router appRouter = Router.router(vertx);
    //bind urls
    appRouter.mountSubRouter("/api/helloapp", new HelloController().getHelloRouterConfig());
    appRouter.mountSubRouter("/api/haiapp", new HaiController().getHaiRouterConfig());

    //route all http requests
    server.requestHandler(appRouter);


    //start
    server.listen(server -> {
      if (server.succeeded()) {
        System.out.println("REST Api Server is Ready!");
      }
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new HelloController());
    vertx.deployVerticle(new HaiController());
    StartApp();
  }
}
