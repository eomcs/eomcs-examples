// 1) 패턴 적용 전
// 2) private 생성자 + Factory Method 설계 패턴
// 3) Abstract Factory 설계 패턴
// 4) Abstract Factory 설계 패턴 + 인터페이스
//
package com.eomcs.design_pattern.test01;

import com.eomcs.design_pattern.test01.step4.Car;
import com.eomcs.design_pattern.test01.step4.CarFactory;
import com.eomcs.design_pattern.test01.step4.K7Factory;
import com.eomcs.design_pattern.test01.step4.SonataFactory;

public class Test04 {
  public static void main(String[] args) {
    SonataFactory sonataFactory = new SonataFactory();
    K7Factory k7Factory = new K7Factory();

    printCar(sonataFactory);
    printCar(k7Factory);
  }

  static void printCar(CarFactory carFactory) {
    Car car = carFactory.createCar(); // CarFactory 사용 규칙에 따라 메서드 실행
    System.out.println(car);
  }
}
