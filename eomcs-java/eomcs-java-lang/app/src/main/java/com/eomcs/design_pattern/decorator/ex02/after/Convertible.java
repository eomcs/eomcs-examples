package com.eomcs.design_pattern.decorator.ex02.after;

public class Convertible extends Decorator {
  
  boolean openRoof;
  
  public Convertible(Car car) {
    super(car);
  }
  
  @Override
  public void run() {
    // 생성자에서 받은 원래의 자동차를 실행한다.
    this.car.run();

    // 생성자에서 받은 자동차에 덧붙인 자동차 지붕 열기 기능을 실행한다.
    if (this.openRoof) {
      System.out.println("지붕을 연다!");
    } else {
      System.out.println("지붕을 닫는다.");
    }
  }
  
  public void openRoof(boolean openRoof) {
    this.openRoof = openRoof;
  }
  
}





