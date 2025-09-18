package com.eomcs.design_pattern.decorator.ex02.after;

public class Truck extends Car {
  int weight;
  
  @Override
  public void run() {
    System.out.println("덜컬덜컹 달린다!");
  }
}
