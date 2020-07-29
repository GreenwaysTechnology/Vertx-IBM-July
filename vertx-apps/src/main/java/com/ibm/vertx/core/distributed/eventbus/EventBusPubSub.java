package com.ibm.vertx.core.distributed.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.example.util.Runner;


class HealthEmerencyVerticle extends AbstractVerticle {

  private void requestReply() {
    EventBus eventBus = vertx.eventBus();
    //Declare Consumer
    MessageConsumer<String> consumer = eventBus.consumer("alert.tn.covid");
    //handle/process the message/news
    consumer.handler(alert -> {
      System.out.println("Alert From Hosiptial : " + alert.body());
      //reply sent to publisher
      alert.reply("Patient is crictal!! Need More Attention!!!");
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    requestReply();
  }
}

//
class CentralFinanceVerticle extends AbstractVerticle {

  private void alertNotification() {
    EventBus eventBus = vertx.eventBus();
    //Declare Consumer
    MessageConsumer<String> consumer = eventBus.consumer("notification.tn.covid");
    //handle/process the message/news
    consumer.handler(news -> {
      System.out.println("Alert From Tamil Nadu : " + news.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    alertNotification();
  }
}

//publisher
class NewsPublisherVerticle extends AbstractVerticle {

  //pub and sub
  private void publishNews() {
    //pub-sub : publish method on event bus identicates
    vertx.setTimer(5000, ar -> {
      //publish message
      String news = "Last 24 hrs, 50000 covid patients in India";
      vertx.eventBus().publish("news.in.covid", news);
    });

  }

  //point to point
  private void sendNotification() {
    vertx.setTimer(1500, h -> {
      //point-to-point
      vertx.eventBus().send("notification.tn.covid", "We have not received any update on Fund!");
    });
  }
  private void sendAlert() {
    vertx.setTimer(100, h -> {
      //request-reply
      vertx.eventBus().request("alert.tn.covid", "We have send medical Reports of Mr X", ar -> {
        if (ar.succeeded()) {
          System.out.println("Reply/Response : " + ar.result().body());
        }
      });
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    publishNews();
    sendNotification();
    sendAlert();
  }
}

//subscriber-1
class NewsSevenVerticle extends AbstractVerticle {

  private void consumeNews() {
    EventBus eventBus = vertx.eventBus();
    //Declare Consumer
    MessageConsumer<String> consumer = eventBus.consumer("news.in.covid");
    //handle/process the message/news
    consumer.handler(news -> {
      System.out.println("News 7's Today News : " + news.body());
    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    consumeNews();
  }
}

//subscriber-2
class BBCVerticle extends AbstractVerticle {

  private void consumeNews() {
    EventBus eventBus = vertx.eventBus();
    //Declare Consumer
    MessageConsumer<String> consumer = eventBus.consumer("news.in.covid");
    //handle/process the message/news
    consumer.handler(news -> {
      System.out.println("BCC Today News : " + news.body());
    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    consumeNews();
  }
}

public class EventBusPubSub extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(EventBusPubSub.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //deploy all the verticles
    vertx.deployVerticle(new NewsPublisherVerticle());
    vertx.deployVerticle(new NewsSevenVerticle());
    vertx.deployVerticle(new BBCVerticle());
    vertx.deployVerticle(new CentralFinanceVerticle());
    vertx.deployVerticle(new HealthEmerencyVerticle());

  }
}
