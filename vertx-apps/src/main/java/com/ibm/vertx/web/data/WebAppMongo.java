package com.ibm.vertx.web.data;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;

class BooksRepository extends AbstractVerticle {
  MongoClient mongoClient;

  public BooksRepository() {
  }

  public BooksRepository(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  //biz api :return records via promise
  public Future<String> findAll() {
    Promise promise = Promise.promise();
    mongoClient.find("books", new JsonObject(), listAsyncResult -> {
      if (listAsyncResult.succeeded()) {
        //Store results in jsonArray
        JsonArray documents = new JsonArray();
        for (JsonObject document : listAsyncResult.result()) {
          documents.add(document);
        }
        promise.complete(documents.encodePrettily());

      } else {
        promise.fail(listAsyncResult.cause().getMessage());
      }
    });


    return promise.future();
  }
}

class BooksController extends AbstractVerticle {

  private BooksRepository bookRepository;

  public BooksController() {
  }

  public BooksController(MongoClient mongoClient) {
    bookRepository = new BooksRepository(mongoClient);
  }

  //booksRouter
  public Router getBooksConfig() {
    //sub Routers
    Router router = Router.router(vertx);
    //end points
    router.get("/list").handler(ctx -> {

      //talk to book repo class to get records async, once the result is ready,send to client
      bookRepository.findAll().onComplete(ar -> {
        //verify result
        if (ar.succeeded()) {
          //set Header content type
          ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
          //flush the response
          ctx.response().end(ar.result());
        } else {
          ctx.response().end("Sorry Somthing went Wrong!!!!");
        }

      });



    });

    return router;
  }
}


public class WebAppMongo extends AbstractVerticle {

  HttpServer server;
  HttpServerOptions options;
  MongoClient mongoClient;

  public static void main(String[] args) {
    Runner.runExample(WebAppMongo.class);
  }

  public void StartApp() {
    options = new HttpServerOptions().setPort(3001).setHost("localhost");
    server = vertx.createHttpServer(options);

    //Mongo db connection

    //connecting mongo db
    mongoClient = MongoClient.createShared(vertx, new JsonObject().put("db_name", "booksdb"));
    BooksController booksController = new BooksController(mongoClient);


    //Router Registeration
    Router appRouter = Router.router(vertx);
    //bind urls
    appRouter.mountSubRouter("/api/books", booksController.getBooksConfig());
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
    StartApp();
  }
}
