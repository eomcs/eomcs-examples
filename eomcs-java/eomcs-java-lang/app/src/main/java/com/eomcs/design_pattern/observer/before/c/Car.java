package com.eomcs.design_pattern.observer.before.c;

public class Car {
  public void start() {
    System.out.println("시동을 건다.");

    // 예) 1월 20일 - 자동차 시동을 걸 때 안전 벨트 착용 여부를 검사하는 기능을 추가
    System.out.println("안전벨트 착용 여부 검사");

    // 예) 2월 30일 - 자동차 시동을 걸 때 엔진 오일 유무를 검사하는 기능을 추가
    System.out.println("엔진 오일 유무 검사");
  }

  public void run() {
    System.out.println("달린다.");
  }

  public void stop() {
    System.out.println("시동을 끈다.");
  }
}


