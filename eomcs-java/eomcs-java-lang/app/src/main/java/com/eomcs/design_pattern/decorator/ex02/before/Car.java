package com.eomcs.design_pattern.decorator.ex02.before;

public abstract class Car {
  protected int speed;
  
  public void start() {
    System.out.println("시동 건다!");
  }
  
  public void stop() {
    System.out.println("시동 끈다!");
  }
  
  public abstract void run();
}
