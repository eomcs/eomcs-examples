package com.eomcs.ioc.ex08;

public class Car {
  Engine engine;

  public Car() {
    System.out.println("===> Car()");
    this.engine = new Engine();
  }

  public void move() {
    this.engine.run();
  }
}
