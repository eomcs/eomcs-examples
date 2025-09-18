// 상속 : specialization
package com.eomcs.oop.ex05.j;

// Car 클래스를 만든 이후에 승용차나 트럭에 대해
// 좀 더 섬세하게 제어하기 위해서
// Car 클래스를 상속 받아 Sedan, Truck 클래스를 추가로 정의하게 되었다.
//
// 이렇게 기존 클래스에 기능을 덧붙여 특별한 클래스를 만드는 것
// - 수퍼 클래스를 상속 받아 서브 클래스를 만드는 것을 "전문화(specialization)"라 부른다.
//
public class Exam02 {

  public static void main(String[] args) {
    // 이전에 만들었던 Car 클래스를 사용할 수도 있고,
    Car generalCar = new Car();
    generalCar.run();

    // 좀 더 전문화해서 만든 Sedan, Truck 클래스도 사용할 수 있다.
    Sedan s = new Sedan();
    s.doSunroof(true);

    Truck t = new Truck();
    t.dump();

  }

}
