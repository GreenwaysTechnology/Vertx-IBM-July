package com.ibm.vertx.core.dataformats;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.example.util.Runner;

public class BufferDemo  extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BufferDemo.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //Buffer
    Buffer buffer = Buffer.buffer();
    buffer.appendString("Hello");
    buffer.appendString("Hai");
    buffer.appendString("Welcome");
    buffer.appendString("How are you");
    buffer.appendString("this is binary data");
    System.out.println(buffer.length());
    System.out.println(buffer.toString());
  }
}
