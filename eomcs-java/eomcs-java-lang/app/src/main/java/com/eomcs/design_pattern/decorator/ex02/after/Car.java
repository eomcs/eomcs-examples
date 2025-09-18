package com.eomcs.design_pattern.decorator.ex02.after;

public abstract class Car {
  protected int speed;
  protected int capacity;
  
  public void start() {
    System.out.println("시동 건다!");
  }
  
  public void stop() {
    System.out.println("시동 끈다!");
  }
  
  public abstract void run();
}
