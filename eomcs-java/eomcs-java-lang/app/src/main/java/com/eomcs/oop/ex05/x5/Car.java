package com.eomcs.oop.ex05.x5;

public abstract class Car {
  public void start() {
    System.out.println("시동 건다!");
  }

  public void stop() {
    System.out.println("시동 끈다!");
  }

  public abstract void run();
}
