package com.ibm.vertx.distributed.config;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

class JSONFileSystemConfig extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //Add Storage options: type, format,file path
    ConfigStoreOptions options = new ConfigStoreOptions();
    options.setType("file");
    options.setFormat("json");
    //file path
    options.setConfig(new JsonObject().put("path", "application.json"));
    ConfigRetriever retriever = ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(options));


    retriever.getConfig(config -> {
      if (config.succeeded()) {
        System.out.println("Config is Ready");
        //System.out.println(config.result());
        JsonObject configRes = config.result();
        System.out.println(configRes.getJsonObject("application").getString("name"));
        System.out.println(configRes.getJsonObject("application").getInteger("port"));

      } else {
        System.out.println("Config Error : " + config.cause());
      }
    });

  }
}

public class ConfigMainApp extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ConfigMainApp.class);
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(new JSONFileSystemConfig());
  }
}
