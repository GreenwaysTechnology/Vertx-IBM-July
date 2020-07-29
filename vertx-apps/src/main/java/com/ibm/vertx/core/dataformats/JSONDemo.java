package com.ibm.vertx.core.dataformats;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

class Vistior extends AbstractVerticle {

  public Future<JsonObject> getVisitor() {
    Promise promise = Promise.promise();
    JsonObject address = new JsonObject();
    address.put("city", "coimbatore").put("street", "10th street").put("state", "Tamil Nadu");
    JsonObject visitor = new JsonObject();
    visitor.put("name", "subramanian").put("address", address);
    promise.complete(visitor);
    return promise.future();
  }

  public Future<JsonArray> getVisitorList() {
    Promise promise = Promise.promise();

    JsonObject address = new JsonObject();
    address.put("city", "coimbatore").put("street", "10th street").put("state", "Tamil Nadu");
    JsonObject visitor = new JsonObject();
    visitor.put("name", "subramanian").put("address", address);

    JsonArray list = new JsonArray();
    list.add(visitor).add(visitor).add(visitor).add(visitor);
    promise.complete(list);

    return promise.future();

  }

  @Override
  public void start() throws Exception {
    super.start();
    getVisitor().onComplete(jsonObjectAsyncResult -> {
      System.out.println(jsonObjectAsyncResult.result().encodePrettily());
    });
    getVisitorList().onComplete(jsonObjectAsyncResult -> {
      System.out.println(jsonObjectAsyncResult.result().encodePrettily());
    });
    getVisitorList().onComplete(jsonObjectAsyncResult -> {
      jsonObjectAsyncResult.result().forEach(visitor -> {
        System.out.println(visitor);
      });
    });
  }
}

public class JSONDemo extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(JSONDemo.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new Vistior());
  }
}
