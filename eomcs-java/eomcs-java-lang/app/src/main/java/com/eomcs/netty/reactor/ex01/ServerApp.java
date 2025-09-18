package com.eomcs.netty.reactor.ex01;

import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

public class ServerApp {
  public static void main(String[] args) throws Exception {
    DisposableServer server = HttpServer
        .create()
        .port(8888)
        //.handle((req, resp) -> resp.sendString(Mono.just("Hello, world!")))
        .route(r -> r.get("/hello/{param}", (req, resp) -> resp.sendString(Mono.just("hello..."))))
        .bindNow();

    server.onDispose()
    .block();
  }
}
