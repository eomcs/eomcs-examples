package com.eomcs.reactive;

import reactor.core.publisher.Flux;

public class Exam01 {

  public static void main(String[] args) {
    Flux<String> flux = Flux.just("A");
    Flux<String> flux2 = flux.map(s -> "foo" + s);

    //    flux.subscribe(System.out::println);
    flux2.subscribe(System.out::println);
  }

}
