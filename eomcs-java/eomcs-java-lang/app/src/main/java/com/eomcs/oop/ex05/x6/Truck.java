package com.eomcs.oop.ex05.x6;

public class Truck extends Car {
  int weight;

  @Override
  public void run() {
    // 기존에 이미 있는 메서드를 호출한다.
    this.go();
  }

  @Override
  public void start() {
    this.launch();
  }

  @Override
  public void stop() {
    this.stopping();
  }

  public void launch() {
    System.out.println("트럭 시동 건다!");
  }

  public void stopping() {
    System.out.println("트럭 시동 끈다!");
  }

  public void go() {
    System.out.println("덜덜덜 달린다.");
  }

  public void dump() {
    System.out.println("짐을 내린다.");
  }
}
