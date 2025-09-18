package com.eomcs.design_pattern.decorator.ex02.after;

// 자동차에 추가 기능을 덧붙이는 역할을 한다.
// 다른 부속품의 수퍼 클래스 역할을 한다. 
// => 그래서 추상 클래스로 정의한다.
public abstract class Decorator extends Car {
  
  Car car;
  
  // 비록 Car를 상속 받았지만 스스로 Car 일을 하지는 않는다.
  // 단지 다른 자동차에 기능을 덧붙이다.
  // => 생성자에서 반드시 기능을 덧불일 대상이 되는 자동차를 받아야 한다.
  public Decorator(Car car) {
    this.car = car;
  }
}
