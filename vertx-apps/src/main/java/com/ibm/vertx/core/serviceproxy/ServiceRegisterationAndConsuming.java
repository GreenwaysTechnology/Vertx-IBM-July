package com.ibm.vertx.core.serviceproxy;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;
import io.vertx.serviceproxy.ServiceBinder;

public class ServiceRegisterationAndConsuming extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ServiceRegisterationAndConsuming.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //Service Registeration
    GreeterService greeterService = new GreeterServiceImpl();
    //Registre interface with Event Bus so that other people can lookup
    new ServiceBinder(vertx).setAddress(GreeterService.ADDRESS).register(GreeterService.class, greeterService);

    //consumer code
    GreeterService service = GreeterService.createProxy(vertx, GreeterService.ADDRESS);
    service.sayHello("Subramanian", ar -> {
      if(ar.succeeded()){
        System.out.println(ar.result());
      }
    });
  }
}
