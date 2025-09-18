package com.eomcs.oop.ex05.k;

public class Sedan {
  public void start() {
    System.out.println("시동 건다!");
  }
  public void shutdown() {
    System.out.println("시동 끈다!");
  }
  public void run() {
    System.out.println("쌩쌩 달린다.");
  }
  public void doSunroof(boolean open) {
    if (open) {
      System.out.println("썬루프를 연다.");
    } else {
      System.out.println("썬루프를 닫는다.");
    }
  }
}
