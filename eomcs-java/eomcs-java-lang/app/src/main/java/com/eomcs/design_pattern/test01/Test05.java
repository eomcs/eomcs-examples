// 1) 패턴 적용 전
// 2) private 생성자 + Factory Method 설계 패턴
// 3) Abstract Factory 설계 패턴
// 4) Abstract Factory 설계 패턴 + 인터페이스
// 5) Singleton 설계 패턴
//
package com.eomcs.design_pattern.test01;

import com.eomcs.design_pattern.test01.step5.Car;
import com.eomcs.design_pattern.test01.step5.CarFactory;
import com.eomcs.design_pattern.test01.step5.K7Factory;
import com.eomcs.design_pattern.test01.step5.SonataFactory;

public class Test05 {
  public static void main(String[] args) {
    SonataFactory sonataFactory = SonataFactory.getInstance();
    K7Factory k7Factory = K7Factory.getInstance();
    K7Factory k7Factory2 = K7Factory.getInstance();
    K7Factory k7Factory3 = K7Factory.getInstance();

    // 객체는 오직 한 개만 생성된다.
    System.out.println(k7Factory == k7Factory2);
    System.out.println(k7Factory == k7Factory3);

    printCar(sonataFactory);
    printCar(k7Factory);
  }

  static void printCar(CarFactory carFactory) {
    Car car = carFactory.createCar(); // CarFactory 사용 규칙에 따라 메서드 실행
    System.out.println(car);
  }
}
