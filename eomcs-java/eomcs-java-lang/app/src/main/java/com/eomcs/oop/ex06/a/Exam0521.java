// 다형성 - 다형적 변수의 활용
package com.eomcs.oop.ex06.a;

public class Exam0521 {

  public static void main(String[] args) {
    // 수퍼 클래스의 레퍼런스는 하위 클래스의 인스턴스를 담을 수 있다.
    Vehicle[] arr = new Vehicle[] {
        new Car("비트자동차", 5, 1980, 16), 
        new Bike("캠프모터", 5, true), 
        new Sedan("현대자동차", 5, 1980, 16, true, true), 
        new Truck("기아자동차", 5, 10000, 32, 15f, true)};

    for (int i = 0; i < arr.length; i++) {
      printCar(arr[i]);
    }
  }

  static void printCar(Vehicle v) {
    System.out.printf("모델: %s\n", v.model);
    System.out.printf("수용인원: %s\n", v.capacity);

    if (v instanceof Car) {
      Car c = (Car) v;
      System.out.printf("cc: %s\n", c.cc);
      System.out.printf("밸브: %s\n", c.valve);

      if (v instanceof Sedan) {
        Sedan s = (Sedan) v;
        System.out.printf("썬루프: %b\n", s.sunroof);
        System.out.printf("오토: %b\n", s.auto);
      } else if (v instanceof Truck) {
        Truck t = (Truck) v;
        System.out.printf("톤: %f\n", t.ton);
        System.out.printf("덤프여부: %b\n", t.dump);
      }
    } else if (v instanceof Bike) {
      Bike b = (Bike) v;
      System.out.printf("engine: %b\n", b.engine);
    }
    System.out.println("-----------------------");
  }

}
