package com.eomcs.ioc.ex09;

@Component
public class Engine {

  public Engine() {
    System.out.println("===> Engine()");
  }

  public void run() {
    System.out.println("엔진을 가동한다.");
  }
}
