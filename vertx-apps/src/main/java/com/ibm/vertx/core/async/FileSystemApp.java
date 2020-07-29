package com.ibm.vertx.core.async;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import io.vertx.example.util.Runner;

class FileServiceVerticle extends AbstractVerticle {

  private Future<String> readFile() {
    Promise promise = Promise.promise();
    //Async file read operation
    FileSystem fs = vertx.fileSystem();

    fs.readFile("assets/hello.txt", fileHandler -> {
      if (fileHandler.succeeded()) {
        System.out.println("File is ready!");
        promise.complete(fileHandler.result().toString());
      } else {
        promise.fail(fileHandler.cause());
      }
    });


    return promise.future();
  }

  private Future<String> readFileBlocking() {
    Promise promise = Promise.promise();
    //Async file read operation
    FileSystem fs = vertx.fileSystem();
    Buffer result = fs.readFileBlocking("assets/hello.txt");
    promise.complete(result.toString());
    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Start");
//    readFile().onComplete(far -> {
//      if (far.succeeded()) {
//        System.out.println(far.result());
//      } else {
//        System.out.println(far.cause().getMessage());
//      }
//    });
    readFileBlocking()
      .onSuccess(System.out::println)
      .onFailure(System.out::println);
    System.out.println("end");
  }
}

public class FileSystemApp extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(FileSystemApp.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new FileServiceVerticle());
  }
}
