package com.eomcs.design_pattern.decorator.ex02.before;

public class Truck extends Car {
  @Override
  public void run() {
    System.out.println("덜컬덜컹 달린다!");
  }
  
  public void dump() {
    System.out.println("짐을 내린다.");
  }
}
